package org.comfort42.busking.application.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Value;

@Value
@RequiredArgsConstructor
@Getter @Setter
public class BusRoute {

    private final BusRouteId id;


    private final Bus bus;


    private final Route route;
    @Value
    public static class BusRouteId{
        private final Long value;
    }

    public static BusRoute withId(BusRouteId busRouteId,
                                      Bus bus,
                                      Route route
    ) {

        return new BusRoute(busRouteId,bus,route);
    }

}
