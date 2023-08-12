package org.comfort42.busking.persistence.adapter.outbound;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Bus;
import org.comfort42.busking.application.domain.model.Route;
import org.comfort42.busking.application.port.outbound.AssignBusToRoutePort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class AssignBusToRouteAdapter implements AssignBusToRoutePort {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public boolean assignBusToRoute(final Bus.BusId busId, final Route.RouteId routeId) {
        return em.createNativeQuery("INSERT INTO bus_and_route VALUES (?, ?, ?);")
                .setParameter(1, busId.companyId().value())
                .setParameter(2, busId.no())
                .setParameter(3, routeId.getValue())
                .executeUpdate() == 1;
    }
}
