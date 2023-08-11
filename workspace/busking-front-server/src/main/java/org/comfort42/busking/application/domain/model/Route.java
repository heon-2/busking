package org.comfort42.busking.application.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Value;
import org.comfort42.busking.persistence.adapter.outbound.RouteStationJpaEntity;

import java.util.ArrayList;
import java.util.List;

@Value
@RequiredArgsConstructor
@Getter
@Setter
public class Route {

    private final RouteId id;

    private final String name;

    private final Company company;

    private final List<BusRoute> buses;

    private final List<RouteStation> stations;

    private final String geometry;


    @Value
    public static class RouteId {
        private final Long value;

        @Override
        public String toString() {return value.toString();}
    }

    public static Route withId(Route.RouteId routeId,
                               String name,
                               Company company,
                               List<BusRoute> buses,
                               List<RouteStation> stations,
                               String geometry
                               ) {
        return new Route(routeId, name,company,buses,stations,geometry);
    }
}
