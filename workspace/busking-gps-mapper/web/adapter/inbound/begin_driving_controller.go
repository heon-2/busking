package web_inbound

import (
	"errors"
	"net/http"

	"busking.org/gps-mapper/coord"
	sentinel "busking.org/gps-mapper/domain/application/errors"
	"busking.org/gps-mapper/domain/application/model"
	"busking.org/gps-mapper/domain/application/service"
	"github.com/gin-gonic/gin"
	"github.com/gin-gonic/gin/binding"
)

type BeginDrivingRequestBody struct {
	Bus   *BusIdObject `json:"bus" binding:"required"`
	Route *RouteObject `json:"route" binding:"required"`
}

func BeginDrivingController(webAdapter *gin.Engine, drivingService *service.DrivingService) {
	const PATH = "/api/realtime/driving/begin"

	webAdapter.POST(PATH, func(ctx *gin.Context) {
		var payload BeginDrivingRequestBody

		if err := ctx.ShouldBindWith(&payload, binding.JSON); err != nil {
			ctx.AbortWithStatusJSON(http.StatusBadRequest, map[string]interface{}{
				"status":  "400 bad request",
				"message": PATH + ": " + err.Error(),
			})
			return
		}

		if payload.Route.Geometry == nil {
			ctx.AbortWithStatusJSON(http.StatusBadRequest, map[string]interface{}{
				"status":  "400 bad request",
				"message": PATH + ": $.route.geometry is required.",
			})
			return
		}
		if payload.Route.Stations == nil {
			ctx.AbortWithStatusJSON(http.StatusBadRequest, map[string]interface{}{
				"status":  "400 bad request",
				"message": PATH + ": $.route.stations is required.",
			})
			return
		}

		cmd := &service.BeginDrivingCommand{
			BusId: model.BusId{
				CompanyId: *payload.Bus.CompanyId,
				No:        *payload.Bus.No,
			},
			RouteId:       model.RouteId(*payload.Route.Id),
			RouteGeometry: *payload.Route.Geometry,
		}
		for _, latlng := range *payload.Route.Stations {
			cmd.RouteStations = append(cmd.RouteStations, coord.NewLatLng(*latlng.Lat, *latlng.Lng))
		}

		if err := drivingService.BeginDriving(cmd); err != nil {
			if errors.Is(err, sentinel.ErrConflict) {
				ctx.AbortWithStatusJSON(http.StatusConflict, map[string]interface{}{
					"status":  "409 conflict",
					"message": PATH + ": " + err.Error(),
				})
			} else {
				ctx.AbortWithStatusJSON(http.StatusBadRequest, map[string]interface{}{
					"status":  "400 bad request",
					"message": PATH + ": " + err.Error(),
				})
			}
			return
		}

		ctx.JSON(http.StatusCreated, map[string]interface{}{
			"status": "201 created",
		})
	})
}
