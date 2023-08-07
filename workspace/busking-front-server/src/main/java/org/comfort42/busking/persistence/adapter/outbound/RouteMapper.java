package org.comfort42.busking.persistence.adapter.outbound;

import lombok.RequiredArgsConstructor;
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

    Route mapToDomainEntity(
            RouteJpaEntity route
    ) {
        List<RouteStation> routeStations = new ArrayList<>();
        for (RouteStationJpaEntity routeStationJpaEntity : route.getStations()) {
            routeStations.add(routeStationMapper.mapToDomainEntity(routeStationJpaEntity));
        }
        return Route.withId(
                new Route.RouteId(route.getId()),
                route.getName(),
                companyMapper.mapToDomainEntity(route.getCompany()),
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
                new ArrayList<BusRouteJpaEntity>(),
                routeStationJpaEntities,
                route.getGeometry()
        );
    }
}
