package org.comfort42.busking.persistence.adapter.outbound;

import org.comfort42.busking.application.domain.model.Route;
import org.comfort42.busking.application.domain.model.Station;
import org.springframework.stereotype.Component;

@Component
public class RouteMapper {
    Route mapToDomainEntity(
            RouteJpaEntity route
    ) {
        return Route.withId(
                new Route.RouteId(route.getId()),
                route.getName()
        );
    }
}
