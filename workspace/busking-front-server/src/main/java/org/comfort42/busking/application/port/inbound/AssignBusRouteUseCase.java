package org.comfort42.busking.application.port.inbound;

import org.comfort42.busking.application.domain.model.Route;
import org.comfort42.busking.persistence.adapter.outbound.BusPK;

public interface AssignBusRouteUseCase {

    void assignBusRoute(BusPK busPK, Route.RouteId routeId);
}
