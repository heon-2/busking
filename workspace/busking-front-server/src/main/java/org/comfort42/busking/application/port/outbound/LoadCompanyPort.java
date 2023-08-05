package org.comfort42.busking.application.port.outbound;

import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.persistence.adapter.outbound.CompanyJpaEntity;

public interface LoadCompanyPort {

    Company loadCompanyById(Company.CompanyId companyId);
}
