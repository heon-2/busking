package org.comfort42.busking.application.domain.model;

import lombok.*;
import org.comfort42.busking.persistence.adapter.outbound.RouteStationJpaEntity;
import org.comfort42.busking.persistence.adapter.outbound.RouteStationMapper;

import java.util.ArrayList;
import java.util.List;

@Value
@RequiredArgsConstructor
@Getter
@Setter
public class Station {
    private StationId id;

    @NonNull
    private final String name;

    @NonNull
    private final Double lng;

    @NonNull
    private final Double lat;

    private final Company company;

    private final List<RouteStation> routes;

    @Value
    public static class StationId {
        private final long value;

        @Override
        public String toString() {
            return Long.toString(value);
        }

        public static StationId of(final long value) {
            return new StationId(value);
        }
    }

    public static Station withId(StationId stationId,
                                 String name,
                                 Double lng,
                                 Double lat,
                                 Company company,
                                 List<RouteStation> routes
    ) {
        return new Station(stationId, name, lng, lat, company, routes);
    }
}
