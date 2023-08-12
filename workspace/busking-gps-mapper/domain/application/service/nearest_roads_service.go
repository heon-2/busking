package service

import (
	"busking.org/gps-mapper/alg"
	"busking.org/gps-mapper/domain/application/model"
)

type NearestRoadsService struct{}

func (s *NearestRoadsService) NearestRoads(routeId model.RouteId, center alg.Vec2, hw, hh float64) []*alg.Line {
	// route, ok := model.GetRouteManager().GetRoute(routeId)
	// if !ok {
	// 	return nil
	// }

	return nil
	// return route.QueryRoute(alg.NewRectCWH(center, hw, hh))
}

func NewNearestRoadsService() *NearestRoadsService {
	return &NearestRoadsService{}
}
