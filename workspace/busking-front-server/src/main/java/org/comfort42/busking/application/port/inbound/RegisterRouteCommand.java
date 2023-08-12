package org.comfort42.busking.application.port.inbound;

import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.RouteDirection;
import org.comfort42.busking.application.domain.model.Station;

import java.util.List;

public record RegisterRouteCommand(
        Company.CompanyId companyId,
        String routeName,
        String routeGeometry,
        RouteDirection routeDirection,
        List<Station.StationId> stations) {
}
