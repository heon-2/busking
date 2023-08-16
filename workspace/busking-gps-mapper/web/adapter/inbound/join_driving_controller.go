package web_inbound

import (
	"net/http"

	"busking.org/gps-mapper/domain/application/model"
	"busking.org/gps-mapper/domain/application/service"
	"github.com/gin-gonic/gin"
)

type JoinDrivingRequestBody struct {
	Bus         *BusIdObject `json:"bus" binding:"required"`
	Destination int          `json:"destination" binding:"required"`
}

func JoinDrivingController(webAdapter *gin.Engine, drivingService *service.DrivingService) {
	const PATH = "/api/realtime/driving/join"
	webAdapter.POST(PATH, func(ctx *gin.Context) {
		var payload JoinDrivingRequestBody
		if err := ctx.ShouldBindJSON(&payload); err != nil {
			ctx.AbortWithStatusJSON(http.StatusBadRequest, map[string]any{
				"status":  "400 bad request",
				"message": err.Error(),
			})
			return
		}

		cmd := &service.JoinDrivingCommand{
			BusId: model.BusId{
				CompanyId: *payload.Bus.CompanyId,
				No:        *payload.Bus.No,
			},
			Destination: payload.Destination,
		}
		if err := drivingService.JoinDriving(cmd); err != nil {
			ctx.AbortWithStatusJSON(http.StatusBadRequest, map[string]any{
				"status":  "400 bad request",
				"message": err.Error(),
			})
			return
		}

		ctx.AbortWithStatusJSON(http.StatusOK, map[string]any{
			"status": "200 ok",
		})
	})
}
