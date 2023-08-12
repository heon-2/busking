package portout

import (
	"busking.org/gps-mapper/alg"
	"busking.org/gps-mapper/domain/application/model"
)

type SaveBusLocationPort interface {
	Save(bus model.BusId, xy *alg.Vec2)
}
