package org.comfort42.busking.persistence.adapter.outbound;

import org.comfort42.busking.application.domain.model.Station;
import org.springframework.stereotype.Component;

@Component
public class StationMapper {
    Station mapToDomainEntity(
            StationJpaEntity station
    ) {
        return Station.withId(
                new Station.StationId(station.getId()),
                station.getName(),
                station.getLng(),
                station.getLng(),
                station.getRoutes());
    }
}
