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

//    @NonNull
//    private final List<RouteStation> route;

    @Value
    public static class StationId {
        private final Long value;
    }

    public static Station withId(StationId stationId,
                                 String name,
                                 Double lng,
                                 Double lat
                                 ) {

        return new Station(stationId,name,lng,lat);
    }
}
