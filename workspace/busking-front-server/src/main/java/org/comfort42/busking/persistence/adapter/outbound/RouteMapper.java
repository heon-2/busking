package org.comfort42.busking.persistence.adapter.outbound;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Route;

@RequiredArgsConstructor
class RouteMapper {

    private static RouteMapper instance = null;
    private static final CompanyMapper companyMapper = CompanyMapper.getInstance();
    private static final StationMapper stationMapper = StationMapper.getInstance();

    static RouteMapper getInstance() {
        if (instance == null) {
            instance = new RouteMapper();
        }
        return instance;
    }

    Route mapToDomainEntity(RouteJpaEntity routeJpaEntity) {
        final var stations = routeJpaEntity.getStations()
                .stream()
                .map(stationMapper::mapToDomainEntity)
                .toList();

        return new Route(
                Route.RouteId.of(routeJpaEntity.getId()),
                routeJpaEntity.getName(),
                companyMapper.mapToDomainEntity(routeJpaEntity.getCompany()),
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
                route.getStations().stream().map(stationMapper::mapToJpaEntity).toList()
        );
    }

}
