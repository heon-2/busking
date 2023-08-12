package web_inbound

import (
	"net/http"

	"busking.org/gps-mapper/domain/application/service"
	"github.com/gin-gonic/gin"
)

type NearestRoadsRequestBody struct {
	Route      *RouteObject  `json:"route" binding:"required"`
	LatLng     *LatLngObject `json:"latlng" binding:"required"`
	HalfWidth  *float64      `json:"hw" binding:"required"`
	HalfHeight *float64      `json:"hh" binding:"required"`
}

func NearestRoadsController(webAdapter *gin.Engine, nearestRoadsService *service.NearestRoadsService) {
	const PATH = "/api/realtime/nearest-roads"
	// projector := coord.GetProjector()

	webAdapter.POST(PATH, func(ctx *gin.Context) {
		var payload NearestRoadsRequestBody

		if err := ctx.ShouldBindJSON(&payload); err != nil {
			ctx.AbortWithStatusJSON(http.StatusBadRequest, map[string]interface{}{
				"status":  "400 bad request",
				"message": PATH + ": " + err.Error(),
			})
			return
		}

		// roads := nearestRoadsService.NearestRoads(
		// 	model.RouteId(*payload.Route.Id),
		// 	projector.FromWGS84(coord.NewLatLng(*payload.LatLng.Lat, *payload.LatLng.Lng)),
		// 	*payload.HalfWidth,
		// 	*payload.HalfHeight,
		// )

		// roadLatLngs := make([][2][2]float64, 0, len(roads))
		// for i := range roads {
		// 	one := projector.ToWGS84(roads[i].One())
		// 	two := projector.ToWGS84(roads[i].Two())
		// 	roadLatLngs = append(roadLatLngs, [2][2]float64{{one.Lat, one.Lng}, {two.Lat, two.Lng}})
		// }

		ctx.JSON(http.StatusOK, map[string]interface{}{
			"status": "200 ok",
			// "data":   roadLatLngs,
		})
	})
}
