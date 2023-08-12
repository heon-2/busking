package coord

import (
	"busking.org/gps-mapper/alg"
	"github.com/wroge/wgs84"
)

type Projector struct {
	toWGS84   wgs84.Func
	fromWGS84 wgs84.Func
}

var projectorInstance *Projector = nil

func (projector *Projector) ToWGS84(xy *alg.Vec2) *LatLng {
	lng, lat, _ := projector.toWGS84(xy.Y(), xy.X(), 0)
	return NewLatLng(lat, lng)
}

func (projector *Projector) FromWGS84(latlng *LatLng) *alg.Vec2 {
	y, x, _ := projector.fromWGS84(latlng.Lng, latlng.Lat, 0)
	return alg.NewVec2(x, y)
}

func NewProjector() *Projector {
	epsg := wgs84.WGS84().TransverseMercator(127, 38, 1, 200000, 600000)
	return &Projector{
		fromWGS84: wgs84.WGS84().LonLat().To(epsg),
		toWGS84:   wgs84.WGS84().LonLat().From(epsg),
	}
}

func GetProjector() *Projector {
	if projectorInstance == nil {
		projectorInstance = NewProjector()
	}
	return projectorInstance
}
