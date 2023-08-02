package org.comfort42.busking.persistence.adapter.outbound;

import org.comfort42.busking.application.domain.model.Company;

class CompanyMapper {

    private static CompanyMapper instance = null;

    public static CompanyMapper getInstance() {
        if (instance == null) {
            instance = new CompanyMapper();
        }
        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }

    CompanyJpaEntity mapToJpaEntity(final Company company) {
        return new CompanyJpaEntity(company.getId().value(), company.getName(), null);
    }

    Company mapToDomainEntity(final CompanyJpaEntity company) {
        return Company.withId(new Company.CompanyId(company.getId()), company.getName());
    }

}
