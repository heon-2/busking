package org.comfort42.busking.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.RouteStation;
import org.comfort42.busking.application.domain.model.Station;
import org.comfort42.busking.application.port.inbound.RegisterStaionUseCase;
import org.comfort42.busking.application.port.inbound.StationCommand;
import org.comfort42.busking.application.port.outbound.LoadCompanyPort;
import org.comfort42.busking.application.port.outbound.RegisterStationPort;
import org.comfort42.busking.common.UseCase;

import java.util.ArrayList;

@UseCase
@RequiredArgsConstructor
public class RegisterStationService implements RegisterStaionUseCase {

    private final RegisterStationPort registerStationPort;
    private final LoadCompanyPort loadCompanyPort;

    @Override
    public void registerStation(StationCommand stationCommand) {
        Company company=loadCompanyPort.loadCompanyById(stationCommand.getCompanyId());
        registerStationPort.registerStation(new Station(null,stationCommand.getName(),stationCommand.getLng(), stationCommand.getLat(),company,new ArrayList<RouteStation>()));
    }
}
