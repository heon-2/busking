package org.comfort42.busking.persistence.adapter.outbound;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.Route;
import org.comfort42.busking.application.domain.model.RouteStation;
import org.comfort42.busking.application.domain.model.Station;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

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

    private static final CompanyMapper companyMapper=CompanyMapper.getInstance();

    public RouteStation mapToDomainEntity(
            RouteStationJpaEntity routeStationJpaEntity
    ){
        StationJpaEntity stationJpaEntity=routeStationJpaEntity.getStation();
        Station station=Station.withId(new Station.StationId(stationJpaEntity.getId()),
                stationJpaEntity.getName(),
                stationJpaEntity.getLng(),
                stationJpaEntity.getLat(),
                companyMapper.mapToDomainEntity(stationJpaEntity.getCompany()),
                new ArrayList<>());
        RouteJpaEntity routeJpaEntity=routeStationJpaEntity.getRoute();
        Route route=Route.withId(new Route.RouteId(routeJpaEntity.getId()),
                routeJpaEntity.getName(),
                companyMapper.mapToDomainEntity(routeJpaEntity.getCompany()),
                new ArrayList<>(),
                routeJpaEntity.getGeometry()
                );
        return RouteStation.withId(
                new RouteStation.RouteStationId(routeStationJpaEntity.getId()),
                station,
                route
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
