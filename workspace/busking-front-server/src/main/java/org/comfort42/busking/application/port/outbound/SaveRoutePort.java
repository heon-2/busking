package org.comfort42.busking.application.port.outbound;

import org.comfort42.busking.application.domain.model.Route;
import org.comfort42.busking.application.domain.model.Station;
import org.comfort42.busking.persistence.adapter.outbound.SaveRouteCommand;

import java.util.List;

public interface SaveRoutePort {
    void registerRoute(SaveRouteCommand cmd);
}
