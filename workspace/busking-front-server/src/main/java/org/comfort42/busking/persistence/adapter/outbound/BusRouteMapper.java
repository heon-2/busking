package org.comfort42.busking.persistence.adapter.outbound;

import org.comfort42.busking.application.domain.model.BusRoute;
import org.comfort42.busking.application.domain.model.RouteStation;
import org.springframework.stereotype.Component;

@Component
public class BusRouteMapper {

    BusMapper busMapper=new BusMapper();
    RouteMapper routeMapper=new RouteMapper();
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
