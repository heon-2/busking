package org.comfort42.busking.application.port.inbound;

import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.Route;
import org.comfort42.busking.web.adapter.inbound.LoadRouteController;

import java.util.List;

public interface LoadRouteUseCase {

    List<Route> loadAllRoutes(Company.CompanyId companyId);
    Route loadRouteById(Company.CompanyId companyId, Route.RouteId routeId);
}
