package service

import "go.uber.org/fx"

var Module = fx.Module(
	"domain.service",

	fx.Provide(NewNearestRoadsService),
	fx.Provide(NewDrivingService),
)
