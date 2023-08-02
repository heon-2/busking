package org.comfort42.busking.persistence.adapter.outbound;

import org.comfort42.busking.application.domain.model.RouteStation;
import org.springframework.stereotype.Component;

@Component
public class RouteStationMapper {
    StationMapper stationMapper=new StationMapper();
    RouteMapper routeMapper=new RouteMapper();

    public RouteStation mapToDomainEntity(
            RouteStationJpaEntity routeStationJpaEntity,
            StationJpaEntity station,
            RouteJpaEntity route
    ){
        return RouteStation.withId(
                new RouteStation.RouteStationId(routeStationJpaEntity.getId()),
                stationMapper.mapToDomainEntity(station),
                routeMapper.mapToDomainEntity(route)
                );
    }

}
