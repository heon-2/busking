package portout

import (
	"busking.org/gps-mapper/alg"
	"busking.org/gps-mapper/domain/application/model"
)

type SaveLocationEstimationPort interface {
	Save(bus model.BusId, timestamp int64, raw *alg.Vec2, adj *alg.Vec2)
}
