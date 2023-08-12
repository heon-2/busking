package org.comfort42.busking.persistence.adapter.outbound;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class BusRouteMapper {

    private static BusRouteMapper instance = null;
    private static final CompanyMapper companyMapper = CompanyMapper.getInstance();

    public static BusRouteMapper getInstance() {
        if (instance == null) {
            instance = new BusRouteMapper();
        }
        return instance;
    }

    private BusMapper busMapper = new BusMapper();
    private RouteMapper routeMapper = new RouteMapper();

    BusRoute mapToDomainEntity(final BusRouteJpaEntity busRouteJpaEntity) {
        Bus bus = Bus.withId(new Bus.BusId(busRouteJpaEntity.getBus().getId()), busRouteJpaEntity.getBus().getBusNum(), new ArrayList<>());

        Route route = Route.withId(
                new Route.RouteId(busRouteJpaEntity.getId()),
                busRouteJpaEntity.getRoute().getName(),
                companyMapper.mapToDomainEntity(busRouteJpaEntity.getRoute().getCompany()),
                new ArrayList<>(),
                new ArrayList<>(),
                busRouteJpaEntity.getRoute().getGeometry(),
                RouteDirection.UNKNOWN);

        return BusRoute.withId(
                new BusRoute.BusRouteId(busRouteJpaEntity.getId()),
                bus,
                route
        );
    }
}
