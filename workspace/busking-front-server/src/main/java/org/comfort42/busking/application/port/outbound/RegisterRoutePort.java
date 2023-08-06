package org.comfort42.busking.application.port.outbound;

import org.comfort42.busking.application.domain.model.Route;

public interface RegisterRoutePort {

    void registerRoute(Route route);
}
