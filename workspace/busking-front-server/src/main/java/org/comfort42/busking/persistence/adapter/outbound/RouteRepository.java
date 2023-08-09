package org.comfort42.busking.persistence.adapter.outbound;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.Route;
import org.comfort42.busking.application.domain.model.RouteStation;
import org.comfort42.busking.application.domain.model.Station;
import org.comfort42.busking.application.port.outbound.LoadRoutePort;
import org.comfort42.busking.application.port.outbound.RegisterRoutePort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RouteRepository implements RegisterRoutePort, LoadRoutePort {

    @PersistenceContext
    private final EntityManager em;

    private static final CompanyMapper companyMapper=CompanyMapper.getInstance();
    private final StationMapper stationMapper;
    private final RouteMapper routeMapper;


    @Override
    @Transactional
    public void registerRoute(Route route, List<Station> stationList) {
        RouteJpaEntity routeJpaEntity=new RouteJpaEntity();
        routeJpaEntity.setName(route.getName());
        routeJpaEntity.setCompany(companyMapper.mapToJpaEntity(route.getCompany()));
        routeJpaEntity.setGeometry(route.getGeometry());
        em.persist(routeJpaEntity);
//        System.out.println(routeJpaEntity.getId());
        List<RouteStationJpaEntity> routeStationJpaEntities=new ArrayList<>();
        for(Station station:stationList){
            RouteStationJpaEntity routeStationJpaEntity =new RouteStationJpaEntity();
            routeStationJpaEntity.setRoute(routeJpaEntity);
            routeStationJpaEntity.setStation(stationMapper.mapToJpaEntity(station));
            em.persist(routeStationJpaEntity);
        }
        routeJpaEntity.setStations(routeStationJpaEntities);
        em.merge(routeJpaEntity);
    }

    @Override
    public List<Route> loadRouteList(Company.CompanyId companyId) {
        List<RouteJpaEntity> list=em.createQuery("select r from RouteJpaEntity r where r.company.id = :companyId",RouteJpaEntity.class)
                .setParameter("companyId",companyId.value())
                .getResultList();
        List<Route> routeList = new ArrayList<>();
        for(RouteJpaEntity routeJpaEntity: list){
            routeList.add(routeMapper.mapToDomainEntity(routeJpaEntity));
        }
        return routeList;
    }

    @Override
    public Route loadRouteById(Route.RouteId routeId) {
        return routeMapper.mapToDomainEntity(em.find(RouteJpaEntity.class,routeId.getValue()));
    }
}
