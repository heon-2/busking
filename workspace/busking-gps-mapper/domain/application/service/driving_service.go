package service

import (
	"errors"
	"fmt"
	"math"
	"sync"

	"busking.org/gps-mapper/alg"
	"busking.org/gps-mapper/domain/application/model"
	portout "busking.org/gps-mapper/domain/port/outbound"
	"github.com/gammazero/deque"
)

type DrivingWorker struct {
	driving *model.Driving
	jobs    chan *model.Gps
	kill    chan interface{}
	log     *deque.Deque[*model.Gps]

	saveBusLocationPort portout.SaveLocationEstimationPort
}

type DrivingService struct {
	drivingLock        sync.Mutex
	drivingWorkersLock sync.RWMutex
	drivingWorkers     map[model.BusId]*DrivingWorker

	saveBusLocationPort portout.SaveLocationEstimationPort
}

type BeginDrivingCommand struct {
	model.BusId
	model.RouteId
	RouteGeometry string
}

type EndDrivingCommand struct {
	model.BusId
}

type FeedGpsCommand struct {
	model.BusId
	*model.Gps
}

func continuous(args ...*model.Gps) bool { // u<-v<-w
	u := args[0]
	v := args[1]

	dt := float64(u.Timestamp-v.Timestamp) / 1e+3

	if 3.0 < dt {
		return false
	}

	if 3 <= len(args) {
		w := args[2]
		vu := u.Loc.Sub(v.Loc)
		wv := v.Loc.Sub(w.Loc)

		dv := vu.Norm()/float64(u.Timestamp-v.Timestamp)/1e-3 - wv.Norm()/float64(v.Timestamp-w.Timestamp)/1e-3
		if 1.38889 < dv/dt { // a = 1.38889 m/s^2
			return false
		}

		if dt*(math.Pi/4) < wv.Radian(vu) {
			return false
		}
	}

	return true
}

func findNearest(pt0 *alg.Vec2, fractions []*model.RouteFraction) *model.RoutePoint {
	minDist := math.MaxFloat64
	var minDistPoint *model.RoutePoint = nil

	for _, fraction := range fractions {
		one := fraction.Geometry.One()
		two := fraction.Geometry.Two()

		u := two.Sub(one)
		pt := pt0.Sub(one)
		projection := pt.ProjectTo(u, true)

		dist := projection.Sub(pt).Norm()
		if dist < minDist {
			minDist = dist
			minDistPoint = &model.RoutePoint{
				Ref:   fraction,
				Ratio: projection.Norm() / u.Norm(),
				Point: projection.AddSelf(one),
			}
		}
	}

	return minDistPoint
}

func (w *DrivingWorker) Run() {
	for {
		select {
		case gps := <-w.jobs:

			if w.log.Len() == w.log.Cap() {
				w.log.PopFront()
			}
			w.log.PushBack(gps)

			fractions := w.driving.Route.QueryRoute(alg.NewRectCWH(gps.Loc, 50.0, 50.0))
			nearest := findNearest(gps.Loc, fractions)

			if nearest != nil {
				w.saveBusLocationPort.Save(w.driving.BusId, gps.Timestamp, gps.Loc, nearest.Point)
			} else {
				w.saveBusLocationPort.Save(w.driving.BusId, gps.Timestamp, gps.Loc, nil)
			}

		case <-w.kill:
			return
		}
	}
}

func (w *DrivingWorker) Kill() {
	w.kill <- nil
}

func (s *DrivingService) FeedGpsToWorker(cmd *FeedGpsCommand) error {
	drivingWorker, ok := s.getDrivingWorker(cmd.BusId)
	if !ok {
		return errors.New("driving service: worker not found")
	}

	drivingWorker.jobs <- cmd.Gps
	return nil
}

func (s *DrivingService) GetDriving(busId model.BusId) (*model.Driving, bool) {
	driving, ok := model.GetDrivingManager().GetDriving(busId)
	return driving, ok
}

func (s *DrivingService) BeginDriving(cmd *BeginDrivingCommand) error {
	s.drivingLock.Lock()
	defer s.drivingLock.Unlock()

	routes := model.GetRouteManager()
	route, ok := routes.GetRoute(cmd.RouteId)
	if !ok {
		var err error
		route, err = model.NewRouteWithPolyline(cmd.RouteId, cmd.RouteGeometry)
		if err != nil {
			return fmt.Errorf("BeginDriving(): %w", err)
		}

		route = routes.AddRoute(route)
	}

	driving := model.NewDriving(cmd.BusId, route)
	if err := model.GetDrivingManager().AddDriving(driving); err != nil {
		return fmt.Errorf("BeginDriving(): %w", err)
	}

	worker := &DrivingWorker{
		driving: driving,
		jobs:    make(chan *model.Gps),
		kill:    make(chan interface{}),
		log:     deque.New[*model.Gps](16),

		saveBusLocationPort: s.saveBusLocationPort,
	}

	s.drivingWorkers[cmd.BusId] = worker
	go worker.Run()

	return nil
}

func (s *DrivingService) EndDriving(cmd *EndDrivingCommand) {
	s.drivingLock.Lock()
	defer s.drivingLock.Unlock()

	drivings := model.GetDrivingManager()
	if driving, ok := drivings.GetAndDeleteDriving(cmd.BusId); ok {
		s.deleteDrivingWorker(cmd.BusId)
		model.GetRouteManager().DeleteRoute(driving.RouteId)
	}
}

func (s *DrivingService) getDrivingWorker(busId model.BusId) (*DrivingWorker, bool) {
	s.drivingWorkersLock.RLock()
	defer s.drivingWorkersLock.RUnlock()

	drivingWorker, ok := s.drivingWorkers[busId]
	return drivingWorker, ok
}

func (s *DrivingService) deleteDrivingWorker(busId model.BusId) {
	s.drivingWorkersLock.Lock()
	defer s.drivingWorkersLock.Unlock()

	if drivingWorker, ok := s.drivingWorkers[busId]; ok {
		delete(s.drivingWorkers, busId)
		drivingWorker.Kill()
	}
}

func NewDrivingService(saveBusLocationPort portout.SaveLocationEstimationPort) *DrivingService {
	return &DrivingService{
		drivingWorkers:      map[model.BusId]*DrivingWorker{},
		saveBusLocationPort: saveBusLocationPort,
	}
}
