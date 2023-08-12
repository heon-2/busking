package org.comfort42.busking.application.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.comfort42.busking.persistence.adapter.outbound.RouteStationJpaEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class Route {

    private final RouteId id;

    private final String name;

    private final Company company;

    private final List<Station> stations;

    private final String geometry;

    private final RouteDirection routeDirection;


    @Value
    public static class RouteId {
        private final long value;

        @Override
        public String toString() {
            return Long.toString(value);
        }

        public static RouteId of(final long value) {
            return new RouteId(value);
        }
    }

    public static Route withId(Route.RouteId routeId,
                               String name,
                               Company company,
                               List<Station> stations,
                               String geometry,
                               RouteDirection routeDirection) {
        return new Route(routeId, name, company, stations, geometry, routeDirection);
    }
}
