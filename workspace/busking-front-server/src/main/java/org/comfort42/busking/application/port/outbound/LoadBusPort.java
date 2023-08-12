package org.comfort42.busking.application.port.outbound;

import org.comfort42.busking.application.domain.model.Bus;
import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.persistence.adapter.outbound.BusJpaEntity;

import java.util.List;

public interface LoadBusPort {
    List<Bus> loadAllBuses(Company.CompanyId companyId);

    Bus loadBusById(Bus.BusId bus);
}
