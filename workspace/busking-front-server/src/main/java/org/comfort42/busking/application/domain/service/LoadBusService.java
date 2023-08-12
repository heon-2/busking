package org.comfort42.busking.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Bus;
import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.port.inbound.LoadBusUseCase;
import org.comfort42.busking.application.port.inbound.LoadRouteUseCase;
import org.comfort42.busking.application.port.outbound.LoadBusPort;
import org.comfort42.busking.common.UseCase;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class LoadBusService implements LoadBusUseCase {

    private final LoadBusPort loadBusPort;

    @Override
    public List<Bus> loadAllBuses(final Company.CompanyId companyId) {
        return loadBusPort.loadAllBuses(companyId);
    }

    @Override
    public Bus loadBusById(final Bus.BusId id) {
        return loadBusPort.loadBusById(id);
    }
}
