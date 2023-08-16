package org.comfort42.busking.persistence.adapter.outbound;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.Station;
import org.comfort42.busking.application.port.outbound.LoadStationPort;
import org.comfort42.busking.application.port.outbound.RegisterStationPort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
@RequiredArgsConstructor
public class StationRepository implements RegisterStationPort, LoadStationPort {

    @PersistenceContext
    private final EntityManager em;

    private final StationMapper stationMapper;
    private static final CompanyMapper companyMapper = CompanyMapper.getInstance();

    @Override
    @Transactional
    public void registerStation(Station station) {
        StationJpaEntity stationJpaEntity = new StationJpaEntity();
        stationJpaEntity.setName(station.name());
        stationJpaEntity.setLng(station.lng());
        stationJpaEntity.setLat(station.lat());
        CompanyJpaEntity companyJpaEntity = companyMapper.mapToJpaEntity(station.company());
        stationJpaEntity.setCompany(companyJpaEntity);
        em.persist(stationJpaEntity);
    }

    @Override
    public List<Station> loadStationList(Company.CompanyId companyId) {
        List<StationJpaEntity> list = em.createQuery("select s from StationJpaEntity s where s.company.id = :companyId", StationJpaEntity.class)
                .setParameter("companyId", companyId.value())
                .getResultList();
        List<Station> stationList = new ArrayList<>();
        for (StationJpaEntity stationJpaEntity : list) {
            stationList.add(stationMapper.mapToDomainEntity(stationJpaEntity));
        }
        return stationList;
    }

    @Override
    public Station loadStationById(Station.StationId stationId) {
        return stationMapper.mapToDomainEntity(em.find(StationJpaEntity.class, stationId.value()));
    }

    @Override
    public Station loadStationByName(final String stationName) {
        try {
            final var stationJpaEntity = em
                    .createQuery("""
                            SELECT s
                              FROM StationJpaEntity s
                             WHERE s.name = :stationName""", StationJpaEntity.class)
                    .setParameter("stationName", stationName)
                    .getSingleResult();
            return stationMapper.mapToDomainEntity(stationJpaEntity);
        } catch (NoResultException e) {
            throw new NoSuchElementException(e);
        }
    }

}
