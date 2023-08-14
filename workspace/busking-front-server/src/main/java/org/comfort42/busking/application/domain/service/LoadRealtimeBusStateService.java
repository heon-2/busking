package org.comfort42.busking.application.domain.service;

import org.comfort42.busking.application.domain.model.Bus;
import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.RealtimeBusState;
import org.comfort42.busking.application.port.inbound.LoadRealtimeBusState;
import org.comfort42.busking.application.port.outbound.LoadRealtimeBusStatePort;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class LoadRealtimeBusStateService implements LoadRealtimeBusState {

    private final LoadRealtimeBusStatePort loadRealtimeBusStatePort;

    LoadRealtimeBusStateService(final LoadRealtimeBusStatePort loadRealtimeBusStatePort) {
        this.loadRealtimeBusStatePort = loadRealtimeBusStatePort;
    }

    @Override
    public RealtimeBusState loadRealtimeBusState(final Bus.BusId busId) {
        return loadRealtimeBusStatePort.loadRealtimeBusState(busId);
    }

    @Override
    public List<Pair<String, RealtimeBusState>> loadAllRealtimeBusState(Company.CompanyId companyId) {
        return loadRealtimeBusStatePort.loadAllRealtimeBusState(companyId);
    }
}
