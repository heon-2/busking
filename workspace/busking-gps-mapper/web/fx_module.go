package web

import (
	"fmt"
	"net/http"

	web_inbound "busking.org/gps-mapper/web/adapter/inbound"
	"github.com/gin-gonic/gin"
	"go.uber.org/fx"
)

func serve(constructor interface{}) fx.Option {
	return fx.Invoke(constructor)
}

var Module = fx.Module(
	"web",

	fx.Provide(
		fx.Annotate(
			func() *gin.Engine { return gin.Default() },
		),
	),

	fx.Provide(
		fx.Annotate(
			func(handler *gin.Engine) *http.Server {
				return &http.Server{
					Addr:    ":8080",
					Handler: handler,
				}
			},

			fx.OnStart(func(serv *http.Server) {
				fmt.Println("HTTP server is now listening on " + serv.Addr)
				go serv.ListenAndServe()
			}),

			fx.OnStop(func(serv *http.Server) error {
				return serv.Close()
			}),
		),
	),

	fx.Invoke(func(*http.Server) {}),

	serve(web_inbound.DriveController),
	serve(web_inbound.BeginDrivingController),
	serve(web_inbound.EndDrivingController),
	serve(web_inbound.NearestRoadsController),
)
