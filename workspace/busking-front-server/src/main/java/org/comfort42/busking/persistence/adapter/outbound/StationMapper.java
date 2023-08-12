package org.comfort42.busking.persistence.adapter.outbound;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.RouteStation;
import org.comfort42.busking.application.domain.model.Station;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StationMapper {

    private static StationMapper instance = null;

    private static final RouteStationMapper routeStationMapper = RouteStationMapper.getInstance();

    private static final CompanyMapper companyMapper = CompanyMapper.getInstance();

    public static StationMapper getInstance() {
        if (instance == null) {
            instance = new StationMapper();
        }
        return instance;
    }

    Station mapToDomainEntity(
            StationJpaEntity station
    ) {
        List<RouteStation> routeStations = new ArrayList<>();
        for (RouteStationJpaEntity routeStationJpaEntity : station.getRoutes()) {
            routeStations.add(routeStationMapper.mapToDomainEntity(routeStationJpaEntity));
        }
        return Station.withId(
                new Station.StationId(station.getId()),
                station.getName(),
                station.getLng(),
                station.getLat(),
                companyMapper.mapToDomainEntity(station.getCompany()),
                routeStations
        );
    }

    StationJpaEntity mapToJpaEntity(
            Station station
    ) {
        List<RouteStationJpaEntity> routeStationJpaEntities = new ArrayList<>();
        for (RouteStation routeStation : station.getRoutes()) {
            routeStationJpaEntities.add(routeStationMapper.mapToJpaEntity(routeStation));
        }
        return new StationJpaEntity(
                station.getId().getValue(),
                station.getName(),
                station.getLng(),
                station.getLat(),
                companyMapper.mapToJpaEntity(station.getCompany()),
                routeStationJpaEntities
        );

    }

}
