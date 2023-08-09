package org.comfort42.busking.application.port.outbound;

import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.Route;

import java.util.List;

public interface LoadRoutePort {
    List<Route> loadRouteList(Company.CompanyId companyId);

    Route loadRouteById(Route.RouteId routeId);
}
