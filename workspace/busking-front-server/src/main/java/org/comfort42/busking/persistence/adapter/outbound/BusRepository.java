package org.comfort42.busking.persistence.adapter.outbound;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.comfort42.busking.application.domain.model.Bus;
import org.comfort42.busking.application.port.outbound.LoadBusPort;
import org.comfort42.busking.application.port.outbound.RegisterBusPort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class BusRepository implements RegisterBusPort, LoadBusPort {
    @PersistenceContext
    private EntityManager em;


    @Override
    public BusJpaEntity registerBus(Bus bus) {
        BusJpaEntity busJpaEntity=new BusJpaEntity();
        BusPK busPK = bus.getId().getValue();
        busJpaEntity.setId(busPK);
        busJpaEntity.setCompany(em.getReference(CompanyJpaEntity.class,bus.getId().getValue().getCompanyId()));
        em.persist(busJpaEntity);
        return busJpaEntity;
    }

    @Override
    public List<BusJpaEntity> loadBusList() {
        return em.createQuery("select b from BusJpaEntity b", BusJpaEntity.class)
                .getResultList();
    }

    @Override
    public BusJpaEntity loadBusById(Bus.BusId bus) {
        return em.find(BusJpaEntity.class,bus.getValue());
    }
}
