package org.comfort42.busking.application.domain.port.inbound.outbound;

import org.comfort42.busking.application.domain.model.Company;

public interface RegisterCompanyPort {
    Company registerCompany(String companyName);
}
