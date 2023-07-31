package org.comfort42.busking.persistence.adapter.outbound;

import org.comfort42.busking.application.domain.model.Company;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {

    Company mapToDomainEntity(
            CompanyJpaEntity company
    ) {
        return Company.withId(
                new Company.CompanyId(company.getId()),
                company.getName());
    }

}
