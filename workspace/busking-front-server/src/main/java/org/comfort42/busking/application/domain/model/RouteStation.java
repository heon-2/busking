package org.comfort42.busking.application.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Value;
import org.comfort42.busking.persistence.adapter.outbound.RouteJpaEntity;
import org.comfort42.busking.persistence.adapter.outbound.RouteStationJpaEntity;
import org.comfort42.busking.persistence.adapter.outbound.StationJpaEntity;

import java.util.List;

@Value
@RequiredArgsConstructor
@Getter
@Setter
public class RouteStation {

    private RouteStationId id;

    private final Station station;

    private final Route route;

    @Value
    public static class RouteStationId {
        private final Long value;
    }

    public static RouteStation withId(RouteStationId routeStationId,
                                      Station station,
                                      Route route
    ) {

        return new RouteStation(routeStationId, station, route);
    }
}
