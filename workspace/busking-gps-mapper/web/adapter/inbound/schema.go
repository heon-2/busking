package web_inbound

type LatLngObject struct {
	Lat *float64 `json:"lat" binding:"required"`
	Lng *float64 `json:"lng" binding:"required"`
}

type BusIdObject struct {
	CompanyId *int64 `json:"companyId" binding:"required"`
	No        *int64 `json:"no" binding:"required"`
}

type RouteObject struct {
	Id       *int64          `json:"id" binding:"required"`
	Geometry *string         `json:"geometry"`
	Stations *[]LatLngObject `json:"stations"`
}
