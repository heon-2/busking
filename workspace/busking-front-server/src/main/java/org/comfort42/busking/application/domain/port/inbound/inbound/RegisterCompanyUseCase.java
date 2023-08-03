package org.comfort42.busking.application.domain.port.inbound.inbound;

import org.comfort42.busking.application.domain.model.Company;

public interface RegisterCompanyUseCase {
    Company registerCompany(String companyName);
}
