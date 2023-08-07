package org.comfort42.busking.persistence.adapter.outbound;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.BusRoute;
import org.comfort42.busking.application.domain.model.Route;
import org.comfort42.busking.application.domain.model.RouteStation;
import org.comfort42.busking.application.domain.model.Station;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RouteMapper {

    private static final CompanyMapper companyMapper = CompanyMapper.getInstance();
    private static final RouteStationMapper routeStationMapper=RouteStationMapper.getInstance();
    private static final BusRouteMapper busRouteMapper=BusRouteMapper.getInstance();

    Route mapToDomainEntity(
            RouteJpaEntity route
    ) {
        List<RouteStation> routeStations = new ArrayList<>();
        for (RouteStationJpaEntity routeStationJpaEntity : route.getStations()) {
            routeStations.add(routeStationMapper.mapToDomainEntity(routeStationJpaEntity));
        }
        List<BusRoute> busRoutes=new ArrayList<>();
        for(BusRouteJpaEntity busRouteJpaEntity:route.getBuses()){
            busRoutes.add(busRouteMapper.mapToDomainEntity(busRouteJpaEntity));
        }

        return Route.withId(
                new Route.RouteId(route.getId()),
                route.getName(),
                companyMapper.mapToDomainEntity(route.getCompany()),
                busRoutes,
                routeStations,
                route.getGeometry()
        );
    }

    RouteJpaEntity mapToJpaEntity(
            Route route
    ){
        List<RouteStationJpaEntity> routeStationJpaEntities=new ArrayList();
        for(RouteStation routeStation:route.getStations()){
            routeStationJpaEntities.add(routeStationMapper.mapToJpaEntity(routeStation));
        }
        return new RouteJpaEntity(
                route.getId().getValue(),
                route.getName(),
                companyMapper.mapToJpaEntity(route.getCompany()),
                new ArrayList<BusRouteJpaEntity>(), // 사용할 때 수정 필요
                routeStationJpaEntities,
                route.getGeometry()
        );
    }
}
