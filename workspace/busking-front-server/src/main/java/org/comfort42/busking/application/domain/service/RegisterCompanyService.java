package org.comfort42.busking.application.domain.service;

import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.port.inbound.inbound.RegisterCompanyUseCase;
import org.comfort42.busking.application.domain.port.inbound.outbound.RegisterCompanyPort;
import org.springframework.stereotype.Service;

@Service
public class RegisterCompanyService implements RegisterCompanyUseCase {

    private final RegisterCompanyPort registerCompanyPort;

    private RegisterCompanyService(final RegisterCompanyPort registerCompanyPort) {
        this.registerCompanyPort = registerCompanyPort;
    }

    @Override
    public Company registerCompany(final String companyName) {
        return registerCompanyPort.registerCompany(companyName);
    }

}
