package org.comfort42.busking.persistence.adapter.outbound;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.Route;
import org.comfort42.busking.application.port.outbound.LoadRoutePort;
import org.comfort42.busking.application.port.outbound.SaveRoutePort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RouteRepository implements SaveRoutePort, LoadRoutePort {

    private static  final RouteMapper routeMapper = RouteMapper.getInstance();

    @PersistenceContext
    private final EntityManager em;

    @Override
    @Transactional
    public void registerRoute(final SaveRouteCommand cmd) {
        final var stations = cmd.stations()
                .stream()
                .map(
                        stationId -> new StationJpaEntity(
                                stationId.value(),
                                null,
                                null,
                                null,
                                null
                        )
                ).toList();

        final RouteJpaEntity routeJpaEntity = new RouteJpaEntity(
                null,
                cmd.routeName(),
                cmd.companyId().value(),
                cmd.routeGeometry(),
                cmd.routeDirection(),
                null,
                stations
        );

        em.persist(routeJpaEntity);
    }

    @Override
    public List<Route> loadRouteList(Company.CompanyId companyId) {
        List<RouteJpaEntity> list = em.createQuery("""
                        SELECT r
                          FROM RouteJpaEntity r
                          JOIN FETCH r.stations
                          JOIN FETCH r.company
                         WHERE r.company.id = :companyId""", RouteJpaEntity.class)
                .setParameter("companyId", companyId.value())
                .getResultList();
        List<Route> routeList = new ArrayList<>();
        for (RouteJpaEntity routeJpaEntity : list) {
            routeList.add(routeMapper.mapToDomainEntity(routeJpaEntity));
        }
        return routeList;
    }

    @Override
    public Route loadRouteById(Route.RouteId routeId) {
        return routeMapper.mapToDomainEntity(em.find(RouteJpaEntity.class, routeId.getValue()));
    }
}
