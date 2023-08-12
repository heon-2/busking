package persistence_adapter

type LatLng struct {
	Lat float64 `redis:"lat"`
	Lng float64 `redis:"lng"`
}
