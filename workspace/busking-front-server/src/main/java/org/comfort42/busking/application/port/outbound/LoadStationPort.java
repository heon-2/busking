package org.comfort42.busking.application.port.outbound;

import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.Station;

import java.util.List;

public interface LoadStationPort {

    List<Station> loadStationList(Company.CompanyId companyId);

    Station loadStationById(Station.StationId stationId);

    Station loadStationByName(String stationName);
}
