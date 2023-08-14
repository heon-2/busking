package persistence_outbound

import (
	"context"
	"fmt"

	"busking.org/gps-mapper/alg"
	"busking.org/gps-mapper/coord"
	"busking.org/gps-mapper/domain/application/model"
	persistence_adapter "busking.org/gps-mapper/persistence/adapter"
	"github.com/redis/go-redis/v9"
)

type SaveLocationEstimationAdapter struct{ Db *redis.Client }

func (adpt *SaveLocationEstimationAdapter) Save(busId model.BusId, timestamp int64, raw *alg.Vec2, adj *alg.Vec2) {
	latlng := coord.GetProjector().ToWGS84(raw)
	loc := &persistence_adapter.BusLocation{
		At:     timestamp,
		RawLat: latlng.Lat,
		RawLng: latlng.Lng,

		AdjAt: -1,
	}

	if adj != nil {
		latlng = coord.GetProjector().ToWGS84(adj)
		loc.AdjAt = timestamp
		loc.AdjLat = latlng.Lat
		loc.AdjLng = latlng.Lng
	}

	ret := adpt.Db.HSet(context.Background(), "bus:"+busId.String(), loc)
	fmt.Printf("%v\n", ret)
}

func NewSaveBusLocationAdapter(db *redis.Client) *SaveLocationEstimationAdapter {
	return &SaveLocationEstimationAdapter{Db: db}
}
