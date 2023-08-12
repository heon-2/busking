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

    private static final BusRouteMapper busRouteMapper = BusRouteMapper.getInstance();
    private static final CompanyMapper companyMapper = CompanyMapper.getInstance();
    private static final StationMapper stationMapper = StationMapper.getInstance();

    Route mapToDomainEntity(RouteJpaEntity routeJpaEntity) {
        List<BusRoute> busRoutes = new ArrayList<>();
        for (BusRouteJpaEntity busRouteJpaEntity : routeJpaEntity.getBuses()) {
            busRoutes.add(busRouteMapper.mapToDomainEntity(busRouteJpaEntity));
        }

        final var stations = routeJpaEntity.getStations()
                .stream()
                .map(stationMapper::mapToDomainEntity)
                .toList();

        return new Route(
                Route.RouteId.of(routeJpaEntity.getId()),
                routeJpaEntity.getName(),
                companyMapper.mapToDomainEntity(routeJpaEntity.getCompany()),
                busRoutes,
                stations,
                routeJpaEntity.getGeometry(),
                routeJpaEntity.getDirection()
        );
    }

    RouteJpaEntity mapToJpaEntity(Route route) {
        return new RouteJpaEntity(
                route.getId().getValue(),
                route.getName(),
                route.getCompany().id().value(),
                route.getGeometry(),
                route.getRouteDirection(),
                companyMapper.mapToJpaEntity(route.getCompany()),
                null,
                route.getStations().stream().map(stationMapper::mapToJpaEntity).toList()
        );
    }

}
