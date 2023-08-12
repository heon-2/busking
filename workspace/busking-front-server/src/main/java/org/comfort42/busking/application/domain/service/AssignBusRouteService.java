package org.comfort42.busking.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Bus;
import org.comfort42.busking.application.domain.model.Route;
import org.comfort42.busking.application.port.inbound.AssignBusToRouteUseCase;
import org.comfort42.busking.application.port.outbound.AssignBusToRoutePort;
import org.comfort42.busking.common.UseCase;

@UseCase
@RequiredArgsConstructor
public class AssignBusRouteService implements AssignBusToRouteUseCase {

    private final AssignBusToRoutePort assignBusRoutePort;

    @Override
    public boolean assignBusRoute(Bus.BusId busId, Route.RouteId routeId) {
        return assignBusRoutePort.assignBusToRoute(busId, routeId);
    }
}
