package persistence_outbound

import (
	"context"
	"encoding/json"

	"busking.org/gps-mapper/coord"
	"busking.org/gps-mapper/domain/application/model"
	"github.com/redis/go-redis/v9"
)

type SaveDrivingStateAdapter struct{ Db *redis.Client }

func (adpt *SaveDrivingStateAdapter) Save(busId model.BusId, state *model.DrivingState) {
	var raw *map[string]any
	var adj *map[string]any
	if 0 < state.GpsLog.Len() {
		latlng := coord.GetProjector().ToWGS84(&state.GpsLog.Back().Vec2)
		raw = &map[string]any{
			"timestamp": state.GpsLog.Back().Timestamp,
			"latlng": map[string]any{
				"lat": latlng.Lat,
				"lng": latlng.Lng,
			},
		}

		if state.AdjLog.Back() != nil {
			latlng := coord.GetProjector().ToWGS84(&state.AdjLog.Back().Vec2)
			adj = &map[string]any{
				"timestamp": state.AdjLog.Back().Timestamp,
				"latlng": map[string]any{
					"lat": latlng.Lat,
					"lng": latlng.Lng,
				},
			}
		}

	}

	passengers := make([]int, len(state.Passengers))
	for i := range passengers {
		passengers[i] = int(state.Passengers[i].Load())
	}

	snapshot := map[string]any{
		"loc": map[string]any{
			"raw": raw,
			"adj": adj,
		},
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
