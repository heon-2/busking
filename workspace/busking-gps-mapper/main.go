package main

import (
	"busking.org/gps-mapper/domain/application/service"
	"busking.org/gps-mapper/persistence"
	"busking.org/gps-mapper/web"
	"go.uber.org/fx"
)

func main() {
	fx.New(
		service.Module,
		persistence.Module,
		web.Module,
	).Run()
}
