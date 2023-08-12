package org.comfort42.busking.application.port.inbound;

import org.comfort42.busking.application.domain.model.Bus;
import org.comfort42.busking.application.domain.model.Company;

import java.util.List;

public interface LoadBusUseCase {
    List<Bus> loadAllBuses(Company.CompanyId companyId);

    Bus loadBusById(Bus.BusId id);
}
