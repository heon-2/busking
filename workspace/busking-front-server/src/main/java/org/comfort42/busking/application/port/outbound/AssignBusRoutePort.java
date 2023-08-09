package org.comfort42.busking.application.port.outbound;

import org.comfort42.busking.application.domain.model.Route;
import org.comfort42.busking.persistence.adapter.outbound.BusPK;

public interface AssignBusRoutePort {
    void assignBusRoute(BusPK busPK, Route.RouteId routeId);
}
