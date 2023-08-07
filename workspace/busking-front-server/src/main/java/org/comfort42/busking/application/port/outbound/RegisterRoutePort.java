package org.comfort42.busking.application.port.outbound;

import org.comfort42.busking.application.domain.model.Route;
import org.comfort42.busking.application.domain.model.Station;

import java.util.List;

public interface RegisterRoutePort {

    void registerRoute(Route route, List<Station> stationList);
}
