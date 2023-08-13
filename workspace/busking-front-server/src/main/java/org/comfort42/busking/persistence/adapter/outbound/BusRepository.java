package org.comfort42.busking.persistence.adapter.outbound;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.comfort42.busking.application.domain.model.Bus;
import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.port.outbound.LoadBusPort;
import org.comfort42.busking.application.port.outbound.SaveBusCommand;
import org.comfort42.busking.application.port.outbound.SaveBusPort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class BusRepository implements SaveBusPort, LoadBusPort {

    private static final BusMapper busMapper = BusMapper.getInstance();

    @PersistenceContext
    private EntityManager em;

    @Override
    public void saveBus(final SaveBusCommand cmd) {
        final BusJpaEntity busJpaEntity = new BusJpaEntity(
                new BusIdJpaEntity(cmd.busId().companyId().value(), cmd.busId().no()),
                null
        );

        em.persist(busJpaEntity);
    }

    @Override
    public List<Bus> loadAllBuses(final Company.CompanyId companyId) {
        return em
                .createQuery("""
                        SELECT b
                          FROM BusJpaEntity b
                          LEFT JOIN FETCH b.routes AS r
                          LEFT JOIN FETCH r.company
                         WHERE b.id.companyId=:companyId""", BusJpaEntity.class)
                .setParameter("companyId", companyId.value())
                .getResultList()
                .stream()
                .map(busMapper::mapToDomainEntity)
                .toList();
    }

    @Override
    public Bus loadBusById(final Bus.BusId busId) {
        final var busIdJpaEntity = new BusIdJpaEntity(busId.companyId().value(), busId.no());

        final var busJpaEntity = em
                .createQuery("""
                    SELECT b
                      FROM BusJpaEntity b
                      LEFT JOIN FETCH b.routes AS r
                      LEFT JOIN FETCH r.company
                     WHERE b.id=:busId
                """, BusJpaEntity.class)
                .setParameter("busId", busIdJpaEntity)
                .getSingleResult();
        return busMapper.mapToDomainEntity(busJpaEntity);
    }

}
