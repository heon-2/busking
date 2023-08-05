package org.comfort42.busking.persistence.adapter.outbound;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Route;
import org.comfort42.busking.application.domain.model.Station;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RouteMapper {

    private final CompanyMapper companyMapper;

    Route mapToDomainEntity(
            RouteJpaEntity route
    ) {
        return Route.withId(
                new Route.RouteId(route.getId()),
                route.getName(),
                companyMapper.mapToDomainEntity(route.getCompany())
        );
    }
}
