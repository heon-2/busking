package org.comfort42.busking.persistence.adapter.outbound;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Route;
import org.comfort42.busking.application.port.outbound.AssignBusRoutePort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
@Repository
@RequiredArgsConstructor
public class AssignBusRouteAdapter implements AssignBusRoutePort {

    @PersistenceContext
    private EntityManager em;
    @Override
    @Transactional
    public void assignBusRoute(BusPK busPK, Route.RouteId routeId) {
        BusJpaEntity busJpaEntity=em.find(BusJpaEntity.class,busPK);
        RouteJpaEntity routeJpaEntity=em.find(RouteJpaEntity.class,routeId.getValue());
        BusRouteJpaEntity busRouteJpaEntity=new BusRouteJpaEntity();
        busRouteJpaEntity.setBus(busJpaEntity);
        busRouteJpaEntity.setRoute(routeJpaEntity);
        busJpaEntity.getRoutes().add(busRouteJpaEntity);
        routeJpaEntity.getBuses().add(busRouteJpaEntity);
        em.persist(busRouteJpaEntity);
        em.merge(busJpaEntity);
        em.merge(routeJpaEntity);
    }
}
