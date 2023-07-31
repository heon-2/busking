package org.comfort42.busking.application.domain.model;

import lombok.*;
import org.comfort42.busking.persistence.adapter.outbound.RouteStationJpaEntity;

import java.util.List;

@Value
@RequiredArgsConstructor
@Getter
@Setter
public class Station {
    private StationId stationId;

    @NonNull
    private final String name;

    @NonNull
    private final Double lng;

    @NonNull
    private final Double lat;

//    @OneToMany(mappedBy = "station")
//    private List<RouteStation> routes = new ArrayList<>();
    @NonNull
    private final List<RouteStationJpaEntity> route;

    @Value
    public static class StationId {
        private final Long value;
    }

    public static Station withId(StationId stationId,
                                 String name,
                                 Double lng,
                                 Double lat,
                                 List<RouteStationJpaEntity> route) {
        return new Station(stationId,name,lng,lat,route);
    }
}
