package org.comfort42.busking.persistence.adapter.outbound;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.Station;
import org.comfort42.busking.application.port.outbound.LoadStationPort;
import org.comfort42.busking.application.port.outbound.RegisterStationPort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StationRepository implements RegisterStationPort, LoadStationPort {

    @PersistenceContext
    private final EntityManager em;

    private final StationMapper stationMapper;
    private static final CompanyMapper companyMapper=CompanyMapper.getInstance();

    @Override
    @Transactional
    public void registerStation(Station station) {
        StationJpaEntity stationJpaEntity=new StationJpaEntity();
        stationJpaEntity.setName(station.getName());
        stationJpaEntity.setLng(station.getLng());
        stationJpaEntity.setLat(station.getLat());
        CompanyJpaEntity companyJpaEntity=companyMapper.mapToJpaEntity(station.getCompany());
        stationJpaEntity.setCompany(companyJpaEntity);
        em.persist(stationJpaEntity);
    }

    @Override
    public List<Station> loadStationList(Company.CompanyId companyId) {
        List<StationJpaEntity> list=em.createQuery("select s from StationJpaEntity s where s.company.id = :companyId",StationJpaEntity.class)
                .setParameter("companyId",companyId.value())
                .getResultList();
        List<Station> stationList = new ArrayList<>();
        for(StationJpaEntity stationJpaEntity: list){
            stationList.add(stationMapper.mapToDomainEntity(stationJpaEntity));
        }
        return stationList;
    }

    @Override
    public Station loadStationById(Station.StationId stationId) {
        return stationMapper.mapToDomainEntity(em.find(StationJpaEntity.class,stationId.getValue()));
    }
}
