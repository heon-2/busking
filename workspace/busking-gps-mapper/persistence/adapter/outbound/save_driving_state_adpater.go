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
	var adj *map[string]any
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

	latlng := coord.GetProjector().ToWGS84(&state.GpsLog.Back().Vec2)
	snapshot := map[string]any{
		"loc": map[string]any{
			"raw": map[string]any{
				"timestamp": state.GpsLog.Back().Timestamp,
				"latlng": map[string]any{
					"lat": latlng.Lat,
					"lng": latlng.Lng,
				},
			},
			"adj": adj,
		},
	}

	data, err := json.Marshal(snapshot)
	if err != nil {
		println("SaveDrivingStateAdapter: " + err.Error())
	}

	adpt.Db.Set(context.Background(), "bus:"+busId.String(), data, redis.KeepTTL)
}

func NewSaveBusLocationAdapter(db *redis.Client) *SaveDrivingStateAdapter {
	return &SaveDrivingStateAdapter{Db: db}
}
