package org.comfort42.busking.application.port.inbound;

import org.comfort42.busking.application.domain.model.Bus;
import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.RealtimeBusState;
import org.springframework.data.util.Pair;

import java.util.List;

public interface LoadRealtimeBusState {
    RealtimeBusState loadRealtimeBusState(Bus.BusId busId);

    List<Pair<String, RealtimeBusState>> loadAllRealtimeBusState(Company.CompanyId companyId);
}
