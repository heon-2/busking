package org.comfort42.busking.persistence.adapter.outbound;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Route;
import org.comfort42.busking.application.port.outbound.RegisterRoutePort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RouteRepository implements RegisterRoutePort {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public void registerRoute(Route route) {

    }
}
