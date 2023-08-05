package org.comfort42.busking.persistence.adapter.outbound;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Station;
import org.comfort42.busking.application.port.outbound.RegisterStationPort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class StationRepository implements RegisterStationPort {

    @PersistenceContext
    private final EntityManager em;

    private final StationMapper stationMapper;

    @Override
    @Transactional
    public void registerStation(Station station) {
        StationJpaEntity stationJpaEntity=new StationJpaEntity();
        stationJpaEntity.setName(station.getName());
        stationJpaEntity.setLng(station.getLng());
        stationJpaEntity.setLat(station.getLat());
        em.persist(stationJpaEntity);
    }
}
