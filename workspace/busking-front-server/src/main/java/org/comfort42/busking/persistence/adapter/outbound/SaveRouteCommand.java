package org.comfort42.busking.persistence.adapter.outbound;

import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.RouteDirection;
import org.comfort42.busking.application.domain.model.Station;

import java.util.List;

public record SaveRouteCommand(
        Company.CompanyId companyId,
        String routeName,
        String routeGeometry,
        RouteDirection routeDirection,
        List<Station.StationId> stations) {
}
