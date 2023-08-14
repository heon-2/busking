package org.comfort42.busking.application.port.outbound;

import org.comfort42.busking.application.domain.model.Bus;
import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.RealtimeBusState;
import org.springframework.data.util.Pair;

import java.util.List;

public interface LoadRealtimeBusStatePort {
    RealtimeBusState loadRealtimeBusState(final Bus.BusId busId);

    List<Pair<String, RealtimeBusState>> loadAllRealtimeBusState(Company.CompanyId companyId);
}
