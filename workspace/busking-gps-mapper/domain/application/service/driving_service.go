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

	saveBusLocationPort portout.SaveBusLocationPort
}

type DrivingService struct {
	drivingLock        sync.Mutex
	drivingWorkersLock sync.RWMutex
	drivingWorkers     map[model.BusId]*DrivingWorker

	saveBusLocationPort portout.SaveBusLocationPort
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

		dv := vu.Norm()/float64(u.Timestamp-v.Timestamp) - wv.Norm()/float64(v.Timestamp-w.Timestamp)
		if 1.38889 < dv/dt { // a = 1.38889 m/s^2
			return false
		}

		if dt*(math.Pi/4) < wv.Radian(vu) {
			return false
		}
	}

	return true
}

func (w *DrivingWorker) Run() {

	for {
		select {
		case gps := <-w.jobs:

			if w.log.Len() == w.log.Cap() {
				w.log.PopFront()
			}
			w.log.PushBack(gps)

			dp := [16][2]int{}
			dp[0][0] = 0

			for i := 0; i < w.log.Len(); i++ {
				u := w.log.At(i)
				for j := 0; j < i; j++ {
					v := w.log.At(j)
					k := dp[j][1]
					if (k == 0 && continuous(u, v)) || (k != 0 && continuous(u, v, w.log.At(k-1))) {
						if dp[i][0] < dp[j][0]+1 {
							dp[i][0] = dp[j][0] + 1
							dp[i][1] = j
						}
					}
				}
			}

			target := 0
			maxLen := 0
			for i := 0; i < w.log.Len(); i++ {
				if maxLen < dp[i][0] {
					target = i
					maxLen = dp[i][0]
				}
			}

			if dp[target][1] == 0 {
				continue
			}

			y := w.log.At(target)
			x := w.log.At(dp[target][1] - 1) // y<-x

			optimalDist := math.MaxFloat64
			var optimalCandidate *alg.Vec2

			for _, fraction := range w.driving.Route.QueryRoute(alg.NewRectCWH(gps.Loc, 50.0, 50.0)) {
				u := fraction.Geometry.Two().Sub(fraction.Geometry.One())
				if u.Radian(y.Loc.Sub(x.Loc)) <= math.Pi/2 {
					candidate := y.Loc.Sub(fraction.Geometry.One()).ProjectTo(u, true)
					norm := candidate.Norm()
					if norm < optimalDist {
						optimalDist = norm
						optimalCandidate = candidate.AddSelf(fraction.Geometry.One())
					}
				}
			}

			if optimalCandidate != nil {
				w.saveBusLocationPort.Save(w.driving.BusId, optimalCandidate)
			}

		case <-w.kill:
			fmt.Printf("goodbye")
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

func NewDrivingService(saveBusLocationPort portout.SaveBusLocationPort) *DrivingService {
	return &DrivingService{
		drivingWorkers:      map[model.BusId]*DrivingWorker{},
		saveBusLocationPort: saveBusLocationPort,
	}
}
