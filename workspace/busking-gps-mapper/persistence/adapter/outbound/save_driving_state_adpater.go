package persistence_outbound

import (
	"context"
	"encoding/json"

	"busking.org/gps-mapper/alg"
	"busking.org/gps-mapper/coord"
	"busking.org/gps-mapper/domain/application/model"
	"github.com/redis/go-redis/v9"
)

type SaveDrivingStateAdapter struct{ Db *redis.Client }

func mapToLatLngObj(xy *alg.Vec2) *map[string]any {
	if xy == nil {
		return nil
	}

	latlng := coord.GetProjector().ToWGS84(xy)
	return &map[string]any{
		"lat": latlng.Lat,
		"lng": latlng.Lng,
	}
}

func mapToAdjObj(timestamp int64, pt *model.RoutePoint) *map[string]any {
	if timestamp == -1 {
		return nil
	}

	return &map[string]any{
		"timestamp": timestamp,
		"latlng":    mapToLatLngObj(pt.Point),
		"details": map[string]any{
			"index": pt.Ref.Index,
			"ratio": pt.Ratio,
		},
	}
}

func (adpt *SaveDrivingStateAdapter) Save(busId model.BusId, state *model.DrivingState) {
	var loc *map[string]any

	if 0 < state.Logs.Len() {
		latest := state.Logs.Back()

		loc = &map[string]any{
			"raw": map[string]any{
				"timestamp": latest.Raw.Timestamp,
				"latlng":    mapToLatLngObj(&latest.Raw.Vec2),
			},
			"adj": mapToAdjObj(latest.AdjTimestamp, latest.AdjDetails),
		}
	}

	passengers := make([]int, len(state.Passengers))
	for i := range passengers {
		passengers[i] = int(state.Passengers[i].Load())
	}

	snapshot := map[string]any{
		"loc":        loc,
		"passengers": passengers,
	}

	data, err := json.Marshal(snapshot)
	if err != nil {
		println("SaveDrivingStateAdapter: " + err.Error())
		return
	}

	adpt.Db.Set(context.Background(), "bus:"+busId.String(), data, redis.KeepTTL)
}

func NewSaveBusLocationAdapter(db *redis.Client) *SaveDrivingStateAdapter {
	return &SaveDrivingStateAdapter{Db: db}
}
