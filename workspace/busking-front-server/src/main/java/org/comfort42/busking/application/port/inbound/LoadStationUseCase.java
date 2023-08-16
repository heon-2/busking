package org.comfort42.busking.application.port.inbound;

import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.Station;

import java.util.List;

public interface LoadStationUseCase {
    List<StationCommand> loadStationList(Company.CompanyId companyId);
    Station loadStationByName(String stationName);
}
