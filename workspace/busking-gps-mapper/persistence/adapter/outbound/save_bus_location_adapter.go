package persistence_outbound

import (
	"context"

	"busking.org/gps-mapper/alg"
	"busking.org/gps-mapper/coord"
	"busking.org/gps-mapper/domain/application/model"
	persistence_adapter "busking.org/gps-mapper/persistence/adapter"
	"github.com/redis/go-redis/v9"
)

type SaveBusLocationAdapter struct{ Db *redis.Client }

func (adpt *SaveBusLocationAdapter) Save(busId model.BusId, xy *alg.Vec2) {
	latlng := coord.GetProjector().ToWGS84(xy)
	adpt.Db.HSet(context.Background(), "bus:"+busId.String(), &persistence_adapter.LatLng{
		Lat: latlng.Lat,
		Lng: latlng.Lng,
	})
}

func NewSaveBusLocationAdapter(db *redis.Client) *SaveBusLocationAdapter {
	return &SaveBusLocationAdapter{Db: db}
}
