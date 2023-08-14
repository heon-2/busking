package portout

import "busking.org/gps-mapper/domain/application/model"

type ChangeDrivingStatePort interface {
	BeginDriving(model.BusId)
	EndDriving(model.BusId)
}
