package model

import (
	"fmt"
	"strconv"
	"sync"

	"busking.org/gps-mapper/alg"
	"busking.org/gps-mapper/coord"
	"github.com/tidwall/rtree"
	"github.com/twpayne/go-polyline"
)

type RouteId int64

type RoutePoint struct {
	Ref   *RouteFraction
	Ratio float64
	Point *alg.Vec2
}

type RouteFraction struct {
	Index    int
	Geometry *alg.Line
	Distance float64
}

type Route struct {
	RouteId
	RouteFractions []*RouteFraction
	Stations       []*alg.Vec2
	rtree.RTree
}

type RouteRef struct {
	refs  int
	route *Route
}

type RouteManager struct {
	routesLock sync.RWMutex
	routes     map[RouteId]*RouteRef
}

var routeManagerInstance *RouteManager = nil

func (id RouteId) String() string {
	return strconv.FormatInt(int64(id), 10)
}

func (r *Route) QueryRoute(targetArea *alg.Rect) []*RouteFraction {
	var roads []*RouteFraction
	r.Search(*targetArea.Lt, *targetArea.Rb, func(lt, rb [2]float64, data interface{}) bool {
		roads = append(roads, data.(*RouteFraction))
		return true
	})

	return roads
}

func NewRoute(routeId RouteId, vertices []*alg.Vec2, stations []*alg.Vec2) *Route {
	route := &Route{
		RouteId:        routeId,
		RouteFractions: make([]*RouteFraction, 0, len(vertices)),
		Stations:       stations,
	}

	dist := 0.
	for i := 0; i < len(vertices)-1; i++ {
		geometry := alg.NewLine(vertices[i].Clone(), vertices[i+1].Clone())
		dist += geometry.Norm()

		routeFraction := &RouteFraction{
			Index:    i,
			Geometry: geometry,
			Distance: dist,
		}

		bb := routeFraction.Geometry.Bounds()
		route.Insert(*bb.Lt, *bb.Rb, routeFraction)
		route.RouteFractions = append(route.RouteFractions, routeFraction)
	}

	return route
}

func NewRouteWithPolyline(routeId RouteId, geometry string, stationLatlngs []*coord.LatLng) (*Route, error) {
	data, _, err := polyline.DecodeCoords([]byte(geometry))
	if err != nil {
		return nil, fmt.Errorf("cache route: %w", err)
	}

	vertices := make([]*alg.Vec2, 0, len(data))
	projector := coord.GetProjector()
	for i := range data {
		vertices = append(vertices, projector.FromWGS84(coord.NewLatLng(data[i][0], data[i][1])))
	}

	stations := make([]*alg.Vec2, 0, len(stationLatlngs))
	for _, latlng := range stationLatlngs {
		stations = append(stations, projector.FromWGS84(latlng))
	}

	route := NewRoute(routeId, vertices, stations)
	return route, nil
}

func (routeManager *RouteManager) GetRoute(routeId RouteId) (route *Route, ok bool) {
	routeManager.routesLock.RLock()
	defer routeManager.routesLock.RUnlock()

	routeRef, ok := routeManager.routes[routeId]
	if !ok {
		return nil, ok
	}

	return routeRef.route, ok
}

func (routeManager *RouteManager) AddRoute(route *Route) *Route {
	routeManager.routesLock.Lock()
	defer routeManager.routesLock.Unlock()

	if routeRef, ok := routeManager.routes[route.RouteId]; ok {
		routeRef.refs++
		return routeRef.route
	} else {
		routeManager.routes[route.RouteId] = &RouteRef{
			refs:  1,
			route: route,
		}
		return route
	}
}

func (routeManager *RouteManager) DeleteRoute(routeId RouteId) {
	routeManager.routesLock.Lock()
	defer routeManager.routesLock.Unlock()

	if routeRef, ok := routeManager.routes[routeId]; ok {
		routeRef.refs--
		if routeRef.refs == 0 {
			delete(routeManager.routes, routeId)
		}
	}
}

func GetRouteManager() *RouteManager {
	if routeManagerInstance == nil {
		routeManagerInstance = NewRouteManager()
	}
	return routeManagerInstance
}

func NewRouteManager() *RouteManager {
	return &RouteManager{routes: map[RouteId]*RouteRef{}}
}
