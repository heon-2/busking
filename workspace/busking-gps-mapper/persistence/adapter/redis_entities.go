package persistence_adapter

type BusLocation struct {
	At     int64   `redis:"at"`
	RawLat float64 `redis:"rawLat"`
	RawLng float64 `redis:"rawLng"`

	AdjAt  int64   `redis:"adjAt"`
	AdjLat float64 `redis:"adjLat"`
	AdjLng float64 `redis:"adjLng"`
}
