package org.comfort42.busking.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.Station;
import org.comfort42.busking.application.port.inbound.StationCommand;
import org.comfort42.busking.application.port.outbound.LoadStationPort;
import org.comfort42.busking.common.UseCase;
import org.comfort42.busking.application.port.inbound.LoadStationUseCase;

import java.util.ArrayList;
import java.util.List;

@UseCase
@RequiredArgsConstructor
public class LoadStationService implements LoadStationUseCase {

    private final LoadStationPort loadStationPort;

    @Override
    public List<StationCommand> loadStationList(Company.CompanyId companyId) {
        List<StationCommand> stationList = new ArrayList<>();
        for (Station station : loadStationPort.loadStationList(companyId)) {
            stationList.add(new StationCommand(
                    station.getId(),
                    station.getName(),
                    station.getLng(),
                    station.getLat(),
                    station.getCompany().id()));
        }
        return stationList;
    }
}
