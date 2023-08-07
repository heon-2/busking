package org.comfort42.busking.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.Route;
import org.comfort42.busking.application.domain.model.Station;
import org.comfort42.busking.application.port.inbound.RegisterRouteUseCase;
import org.comfort42.busking.application.port.inbound.RouteCommand;
import org.comfort42.busking.application.port.outbound.LoadCompanyPort;
import org.comfort42.busking.application.port.outbound.LoadStationPort;
import org.comfort42.busking.application.port.outbound.RegisterRoutePort;
import org.comfort42.busking.common.UseCase;

import java.util.ArrayList;
import java.util.List;

@UseCase
@RequiredArgsConstructor
public class RegisterRouteService  implements RegisterRouteUseCase {

    private final LoadCompanyPort loadCompanyPort;
    private final RegisterRoutePort registerRoutePort;
    private final LoadStationPort loadStationPort;
    @Override
    public void registerRoute(RouteCommand routeCommand) {
        Company company=loadCompanyPort.loadCompanyById(routeCommand.getCompanyId());
        Route route=new Route(null,routeCommand.getName(),company,null,null,routeCommand.getGeometry());
        List<Station> stationList=new ArrayList<>();
        for(Station.StationId stationId:routeCommand.getStationIds()){
            stationList.add(loadStationPort.loadStationById(stationId));
        }
        registerRoutePort.registerRoute(new Route(null,routeCommand.getName(),company,null,null,routeCommand.getGeometry()),stationList);
    }
}
