package org.comfort42.busking.persistence.adapter.outbound;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.RouteStation;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RouteStationMapper {
    private final StationMapper stationMapper;
    private final RouteMapper routeMapper;

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
