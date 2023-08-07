package org.comfort42.busking.persistence.adapter.outbound;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.RouteStation;
import org.springframework.stereotype.Component;

@Component
public class RouteStationMapper {

    private static RouteStationMapper instance=null;

    public static RouteStationMapper getInstance() {
        if (instance == null) {
            instance = new RouteStationMapper();
        }
        return instance;
    }


    private StationMapper stationMapper=new StationMapper();
    private RouteMapper routeMapper=new RouteMapper();

    public RouteStation mapToDomainEntity(
            RouteStationJpaEntity routeStationJpaEntity
    ){
        return RouteStation.withId(
                new RouteStation.RouteStationId(routeStationJpaEntity.getId()),
                stationMapper.mapToDomainEntity(routeStationJpaEntity.getStation()),
                routeMapper.mapToDomainEntity(routeStationJpaEntity.getRoute())
                );
    }

    public RouteStationJpaEntity mapToJpaEntity(
            RouteStation routeStation
    ){
        return new RouteStationJpaEntity(
                routeStation.getId().getValue(),
                stationMapper.mapToJpaEntity(routeStation.getStation()),
                routeMapper.mapToJpaEntity(routeStation.getRoute())
        );
    }

}
