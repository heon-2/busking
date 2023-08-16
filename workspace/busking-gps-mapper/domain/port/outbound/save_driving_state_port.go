package portout

import "busking.org/gps-mapper/domain/application/model"

type SaveDrivingStatePort interface {
	Save(bus model.BusId, drivingState *model.DrivingState)
}
