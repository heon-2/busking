package model

import (
	"fmt"
	"sync"

	err "busking.org/gps-mapper/domain/application/errors"
)

type Driving struct {
	BusId

	*Route
}

type DrivingManager struct {
	drivingsLock sync.RWMutex
	drivings     map[string]*Driving
}

var drivingManagerInstance *DrivingManager

func NewDriving(busId BusId, route *Route) *Driving {
	return &Driving{busId, route}
}

func (dm *DrivingManager) GetDriving(busId BusId) (*Driving, bool) {
	dm.drivingsLock.RLock()
	defer dm.drivingsLock.RUnlock()

	driving, ok := dm.drivings[busId.String()]
	return driving, ok
}

func (dm *DrivingManager) GetAndDeleteDriving(busId BusId) (*Driving, bool) {
	dm.drivingsLock.Lock()
	defer dm.drivingsLock.Unlock()

	driving, ok := dm.drivings[busId.String()]
	if ok {
		delete(dm.drivings, busId.String())
	}

	return driving, ok
}

func (dm *DrivingManager) AddDriving(driving *Driving) error {
	dm.drivingsLock.Lock()
	defer dm.drivingsLock.Unlock()

	busId := driving.BusId.String()
	if _, ok := dm.drivings[busId]; ok {
		return fmt.Errorf("DrivingManager.AddDriving(): %w", err.ErrConflict)
	}

	dm.drivings[busId] = driving
	return nil
}

func (dm *DrivingManager) DeleteDriving(driving *Driving) {
	dm.drivingsLock.Lock()
	defer dm.drivingsLock.Unlock()

	busId := driving.BusId.String()
	delete(dm.drivings, busId)
}

func GetDrivingManager() *DrivingManager {
	if drivingManagerInstance == nil {
		drivingManagerInstance = NewDrivingManager()
	}
	return drivingManagerInstance
}

func NewDrivingManager() *DrivingManager {
	return &DrivingManager{
		drivings: map[string]*Driving{},
	}
}
