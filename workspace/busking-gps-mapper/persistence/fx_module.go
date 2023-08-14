package persistence

import (
	portout "busking.org/gps-mapper/domain/port/outbound"
	persistence_adapter "busking.org/gps-mapper/persistence/adapter"
	persistence_outbound "busking.org/gps-mapper/persistence/adapter/outbound"
	"go.uber.org/fx"
)

var Module = fx.Module(
	"persistence",

	fx.Provide(fx.Annotate(persistence_outbound.NewSaveBusLocationAdapter, fx.As(new(portout.SaveLocationEstimationPort)))),
	fx.Provide(fx.Annotate(persistence_outbound.NewDrivingRepository, fx.As(new(portout.ChangeDrivingStatePort)))),
	fx.Provide(persistence_adapter.NewRedisClient),
)
