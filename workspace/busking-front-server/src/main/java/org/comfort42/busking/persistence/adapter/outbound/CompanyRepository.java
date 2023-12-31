package org.comfort42.busking.persistence.adapter.outbound;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.port.outbound.LoadCompanyPort;
import org.comfort42.busking.application.port.outbound.RegisterCompanyPort;
import org.springframework.stereotype.Repository;

@Repository
class CompanyRepository implements RegisterCompanyPort, LoadCompanyPort {

    private final static CompanyMapper companyMapper = CompanyMapper.getInstance();

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Company registerCompany(final String companyName) {
        final CompanyJpaEntity company = new CompanyJpaEntity(null, companyName, null);
        em.persist(company);
        return companyMapper.mapToDomainEntity(company);
    }

    @Override
    public Company loadCompanyById(Company.CompanyId companyId) {
        return companyMapper.mapToDomainEntity(em.find(CompanyJpaEntity.class,companyId.value()));
    }
}
