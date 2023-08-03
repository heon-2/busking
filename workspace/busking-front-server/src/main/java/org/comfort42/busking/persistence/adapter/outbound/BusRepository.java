package org.comfort42.busking.persistence.adapter.outbound;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.comfort42.busking.application.domain.model.Bus;
import org.comfort42.busking.application.port.outbound.RegisterBusPort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class BusRepository implements RegisterBusPort {
    @PersistenceContext
    private EntityManager em;


    @Override
    @Transactional
    public BusJpaEntity registerBus(Bus bus) {
        BusJpaEntity busJpaEntity=new BusJpaEntity();
        BusPK busPK = bus.getId().getValue();
        busJpaEntity.setId(busPK);
        busJpaEntity.setCompany(em.getReference(CompanyJpaEntity.class,bus.getId().getValue().getCompanyId()));
        em.persist(busJpaEntity);
        return busJpaEntity;
    }
}
