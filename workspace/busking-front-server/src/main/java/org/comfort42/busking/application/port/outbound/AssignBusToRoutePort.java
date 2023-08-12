package org.comfort42.busking.application.port.outbound;

import org.comfort42.busking.application.domain.model.Bus;
import org.comfort42.busking.application.domain.model.Route;

public interface AssignBusToRoutePort {
    boolean assignBusToRoute(Bus.BusId busId, Route.RouteId routeId);
}
