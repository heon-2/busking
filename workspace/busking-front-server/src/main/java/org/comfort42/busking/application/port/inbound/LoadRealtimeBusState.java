package org.comfort42.busking.application.port.inbound;

import org.comfort42.busking.application.domain.model.Bus;
import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.RealtimeBusState;
import org.springframework.data.util.Pair;

import java.util.List;

public interface LoadRealtimeBusState {
    String loadRealtimeBusState(Bus.BusId busId);

    List<Pair<String, String>> loadAllRealtimeBusState(Company.CompanyId companyId);
}
