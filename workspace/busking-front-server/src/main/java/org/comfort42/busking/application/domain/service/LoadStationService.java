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
                    station.id(),
                    station.name(),
                    station.lng(),
                    station.lat(),
                    station.company().id()));
        }
        return stationList;
    }

    @Override
    public Station loadStationByName(final String stationName) {
        return loadStationPort.loadStationByName(stationName);
    }

}
