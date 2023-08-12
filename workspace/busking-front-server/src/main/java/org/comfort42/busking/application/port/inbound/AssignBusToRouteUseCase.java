package org.comfort42.busking.application.port.inbound;

import org.comfort42.busking.application.domain.model.Bus;
import org.comfort42.busking.application.domain.model.Route;
import org.comfort42.busking.persistence.adapter.outbound.BusIdJpaEntity;

public interface AssignBusToRouteUseCase {
    boolean assignBusRoute(Bus.BusId busId, Route.RouteId routeId);
}
