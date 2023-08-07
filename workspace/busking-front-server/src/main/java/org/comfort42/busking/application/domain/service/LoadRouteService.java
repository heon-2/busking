package org.comfort42.busking.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.Route;
import org.comfort42.busking.application.domain.model.RouteStation;
import org.comfort42.busking.application.domain.model.Station;
import org.comfort42.busking.application.port.inbound.LoadRouteUseCase;
import org.comfort42.busking.application.port.inbound.StationCommand;
import org.comfort42.busking.application.port.outbound.LoadRoutePort;
import org.comfort42.busking.application.port.outbound.LoadStationPort;
import org.comfort42.busking.common.UseCase;
import org.comfort42.busking.web.adapter.inbound.LoadRouteController;

import java.util.ArrayList;
import java.util.List;

@UseCase
@RequiredArgsConstructor
public class LoadRouteService implements LoadRouteUseCase {

    private final LoadRoutePort loadRoutePort;
    private final LoadStationPort loadStationPort;

    @Override
    public List<LoadRouteController.RoutePayload> loadRouteList(Company.CompanyId companyId) {
        List<LoadRouteController.RoutePayload> routes = new ArrayList<>();
        for (Route route : loadRoutePort.loadRouteList(companyId)) {
            List<StationCommand> stationCommands = new ArrayList<>();
            for (RouteStation routeStation : route.getStations()) {
                Station station = loadStationPort.loadStationById(routeStation.getStation().getId());
                stationCommands.add(new StationCommand(station.getId(),
                        station.getName(),
                        station.getLng(),
                        station.getLat(),
                        companyId));
            }
            routes.add(new LoadRouteController.RoutePayload(route.getId(),
                    route.getName(),
                    route.getCompany().getId(),
                    stationCommands));
        }
        return routes;
    }

    @Override
    public LoadRouteController.RoutePayload loadRouteById(Company.CompanyId companyId, Route.RouteId routeId) {
        Route route=loadRoutePort.loadRouteById(routeId);
        List<StationCommand> stationCommands = new ArrayList<>();
        for (RouteStation routeStation : route.getStations()) {
            Station station = loadStationPort.loadStationById(routeStation.getStation().getId());
            stationCommands.add(new StationCommand(station.getId(),
                    station.getName(),
                    station.getLng(),
                    station.getLat(),
                    companyId));
        }
        return new LoadRouteController.RoutePayload(routeId,
                route.getName(),
                companyId,
                stationCommands);
    }
}
