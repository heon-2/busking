package web_inbound

import (
	"net/http"

	"busking.org/gps-mapper/coord"
	"busking.org/gps-mapper/domain/application/model"
	"busking.org/gps-mapper/domain/application/service"
	"github.com/gin-gonic/gin"
)

type DriveRequestBody struct {
	Bus *BusObject `json:"bus" binding:"required"`
	Gps *struct {
		Timestamp *int64        `json:"timestamp" binding:"required"`
		Latlng    *LatLngObject `json:"latlng" binding:"required"`
	} `json:"gps" binding:"required"`
}

func DriveController(c *gin.Engine, drivingService *service.DrivingService) error {
	const PATH = "/api/realtime/driving/drive"
	var projector = coord.GetProjector()

	c.PUT(PATH, func(ctx *gin.Context) {
		var payload DriveRequestBody

		if err := ctx.ShouldBindJSON(&payload); err != nil {
			ctx.AbortWithStatusJSON(http.StatusBadRequest, map[string]interface{}{
				"status":  "400 bad request",
				"message": PATH + ": " + err.Error(),
			})
			return
		}

		cmd := &service.FeedGpsCommand{
			BusId: model.BusId{
				No:        *payload.Bus.No,
				CompanyId: *payload.Bus.CompanyId,
			},
			Gps: &model.Gps{
				Timestamp: *payload.Gps.Timestamp,
				Loc:       projector.FromWGS84(coord.NewLatLng(*payload.Gps.Latlng.Lat, *payload.Gps.Latlng.Lng)),
			},
		}
		if err := drivingService.FeedGpsToWorker(cmd); err != nil {
			ctx.AbortWithStatusJSON(http.StatusBadRequest, map[string]interface{}{
				"status":  "400 bad request",
				"message": PATH + ": " + err.Error(),
			})
			return
		}

		ctx.JSON(http.StatusAccepted, map[string]interface{}{
			"status": "202 accepted",
		})
	})

	return nil
}
