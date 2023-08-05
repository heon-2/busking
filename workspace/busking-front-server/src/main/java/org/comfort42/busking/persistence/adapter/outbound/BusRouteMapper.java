package org.comfort42.busking.persistence.adapter.outbound;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.BusRoute;
import org.comfort42.busking.application.domain.model.RouteStation;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BusRouteMapper {

    private final BusMapper busMapper;
    private final RouteMapper routeMapper;
    BusRoute mapToDomainEntity(
            BusRouteJpaEntity busRouteJpaEntity,
            BusJpaEntity bus,
            RouteJpaEntity route
    ){
        return BusRoute.withId(
                new BusRoute.BusRouteId(busRouteJpaEntity.getId()),
                busMapper.mapToDomainEntity(bus),
                routeMapper.mapToDomainEntity(route)
        );
    }
}
