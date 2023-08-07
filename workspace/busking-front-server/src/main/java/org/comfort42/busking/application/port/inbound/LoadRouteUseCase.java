package org.comfort42.busking.application.port.inbound;

import org.comfort42.busking.web.adapter.inbound.LoadRouteController;

import java.util.List;

public interface LoadRouteUseCase {

    List<LoadRouteController.RoutePayload> loadRouteList();

    LoadRouteController.RoutePayload loadRouteById();
}
