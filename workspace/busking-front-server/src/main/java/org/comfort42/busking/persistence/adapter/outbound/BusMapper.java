package org.comfort42.busking.persistence.adapter.outbound;

import org.comfort42.busking.application.domain.model.Bus;
import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.Route;

import java.util.List;

class BusMapper {
    private static BusMapper instance = new BusMapper();
    private static final RouteMapper routeMapper = RouteMapper.getInstance();

    public static BusMapper getInstance() {
        if (instance == null) {
            instance = new BusMapper();
        }
        return instance;
    }

    public BusJpaEntity mapToJpaEntity(final Bus bus) {
        return new BusJpaEntity(
                new BusIdJpaEntity(bus.getId().companyId().value(), bus.getId().no()),
                bus.getRoutes().stream().map(routeMapper::mapToJpaEntity).toList()
        );
    }

    public Bus mapToDomainEntity(final BusJpaEntity busJpaEntity) {
        List<Route> routes = null;
        if (busJpaEntity.getRoutes() != null) {
            routes = busJpaEntity.getRoutes()
                    .stream()
                    .map(routeMapper::mapToDomainEntity)
                    .toList();
        }

        final Company.CompanyId companyId = Company.CompanyId.of(busJpaEntity.getId().getCompanyId());
        return new Bus(Bus.BusId.of(companyId, busJpaEntity.getId().getNo()), routes);
    }
}
