package web_inbound

import (
	"net/http"

	"busking.org/gps-mapper/domain/application/model"
	"busking.org/gps-mapper/domain/application/service"
	"github.com/gin-gonic/gin"
	"github.com/gin-gonic/gin/binding"
)

type EndDrivingRequestBody struct {
	Bus *BusObject `json:"bus" binding:"required"`
}

func EndDrivingController(webApdatger *gin.Engine, drivingService *service.DrivingService) {
	const PATH = "/api/realtime/driving/end"

	webApdatger.POST(PATH, func(ctx *gin.Context) {
		var payload EndDrivingRequestBody

		if err := ctx.ShouldBindWith(&payload, binding.JSON); err != nil {
			ctx.AbortWithStatusJSON(http.StatusBadRequest, map[string]interface{}{
				"status":  "400 bad request",
				"message": PATH + ": " + err.Error(),
			})
			return
		}

		cmd := &service.EndDrivingCommand{BusId: model.BusId{CompanyId: *payload.Bus.CompanyId, No: *payload.Bus.No}}
		drivingService.EndDriving(cmd)

		ctx.Status(http.StatusNoContent)
	})
}
