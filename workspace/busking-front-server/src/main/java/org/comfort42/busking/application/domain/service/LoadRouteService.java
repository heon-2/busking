package org.comfort42.busking.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.Route;
import org.comfort42.busking.application.port.inbound.LoadRouteUseCase;
import org.comfort42.busking.application.port.outbound.LoadRoutePort;
import org.comfort42.busking.application.port.outbound.LoadStationPort;
import org.comfort42.busking.common.UseCase;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class LoadRouteService implements LoadRouteUseCase {

    private final LoadRoutePort loadRoutePort;
    private final LoadStationPort loadStationPort;

    @Override
    public List<Route> loadAllRoutes(final Company.CompanyId companyId) {
        return loadRoutePort.loadRouteList(companyId);
    }

    @Override
    public Route loadRouteById(Company.CompanyId companyId, Route.RouteId routeId) {
        return loadRoutePort.loadRouteById(routeId);
    }
}
