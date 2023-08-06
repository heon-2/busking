package org.comfort42.busking.application.port.inbound;

import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.port.inbound.StationCommand;

import java.util.List;

public interface LoadStationUseCase {

    List<StationCommand> loadStationList(Company.CompanyId companyId);
}
