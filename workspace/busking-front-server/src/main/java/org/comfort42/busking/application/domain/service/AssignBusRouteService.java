package org.comfort42.busking.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Route;
import org.comfort42.busking.application.port.inbound.AssignBusRouteUseCase;
import org.comfort42.busking.application.port.outbound.AssignBusRoutePort;
import org.comfort42.busking.common.UseCase;
import org.comfort42.busking.persistence.adapter.outbound.BusPK;

@UseCase
@RequiredArgsConstructor
public class AssignBusRouteService implements AssignBusRouteUseCase {

    private final AssignBusRoutePort assignBusRoutePort;

    @Override
    public void assignBusRoute(BusPK busPK, Route.RouteId routeId) {
        assignBusRoutePort.assignBusRoute(busPK,routeId);
    }
}
