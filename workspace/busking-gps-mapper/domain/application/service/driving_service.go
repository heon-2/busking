package service

import (
	"errors"
	"fmt"
	"math"
	"sync"

	"busking.org/gps-mapper/alg"
	"busking.org/gps-mapper/domain/application/model"
	portout "busking.org/gps-mapper/domain/port/outbound"
)

type DrivingWorker struct {
	driving *model.Driving
	jobs    chan *model.Location
	kill    chan interface{}

	saveDrivingStatePort portout.SaveDrivingStatePort
}

type DrivingService struct {
	drivingLock        sync.Mutex
	drivingWorkersLock sync.RWMutex
	drivingWorkers     map[model.BusId]*DrivingWorker

	saveDrivingStatePort   portout.SaveDrivingStatePort
	changeDrivingStatePort portout.ChangeDrivingStatePort
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
	*model.Location
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

func (worker *DrivingWorker) Run() {
	driving := worker.driving

	for {
		select {
		case loc := <-worker.jobs:

			fractions := driving.Route.QueryRoute(alg.NewRectCWH(&loc.Vec2, 50.0, 50.0))
			nearest := findNearest(&loc.Vec2, fractions)

			if driving.State.GpsLog.Len() == driving.State.GpsLog.Cap() {
				driving.State.GpsLog.PopFront()
				driving.State.AdjLog.PopFront()
			}

			driving.State.GpsLog.PushBack(loc)
			if nearest == nil {
				driving.State.AdjLog.PushBack(nil)
			} else {
				driving.State.AdjLog.PushBack(&model.Location{
					Timestamp: loc.Timestamp,
					Vec2:      *nearest.Point,
				})
			}

			worker.saveDrivingStatePort.Save(driving.BusId, driving.State)

		case <-worker.kill:
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

	drivingWorker.jobs <- cmd.Location
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
		jobs:    make(chan *model.Location),
		kill:    make(chan interface{}),

		saveDrivingStatePort: s.saveDrivingStatePort,
	}

	s.changeDrivingStatePort.BeginDriving(cmd.BusId)
	s.drivingWorkers[cmd.BusId] = worker
	go worker.Run()

	return nil
}

func (s *DrivingService) EndDriving(cmd *EndDrivingCommand) {
	s.drivingLock.Lock()
	defer s.drivingLock.Unlock()

	drivings := model.GetDrivingManager()
	if driving, ok := drivings.GetAndDeleteDriving(cmd.BusId); ok {
		s.changeDrivingStatePort.EndDriving(cmd.BusId)
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

func NewDrivingService(
	saveDrivingStatePort portout.SaveDrivingStatePort,
	changeDrivingStatePort portout.ChangeDrivingStatePort) *DrivingService {

	return &DrivingService{
		drivingWorkers:         map[model.BusId]*DrivingWorker{},
		saveDrivingStatePort:   saveDrivingStatePort,
		changeDrivingStatePort: changeDrivingStatePort,
	}
}
