package coord

type LatLng struct{ Lat, Lng float64 }

func NewLatLng(lat, lng float64) *LatLng {
	return &LatLng{Lat: lat, Lng: lng}
}
