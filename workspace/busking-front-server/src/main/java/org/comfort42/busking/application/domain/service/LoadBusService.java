package org.comfort42.busking.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Bus;
import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.Route;
import org.comfort42.busking.application.port.inbound.BusCommand;
import org.comfort42.busking.application.port.inbound.LoadBusUseCase;
import org.comfort42.busking.application.port.inbound.LoadRouteUseCase;
import org.comfort42.busking.application.port.outbound.LoadBusPort;
import org.comfort42.busking.common.UseCase;
import org.comfort42.busking.persistence.adapter.outbound.BusJpaEntity;
import org.comfort42.busking.persistence.adapter.outbound.BusMapper;
import org.comfort42.busking.persistence.adapter.outbound.BusRouteJpaEntity;
import org.comfort42.busking.web.adapter.inbound.LoadRouteController;

import java.util.ArrayList;
import java.util.List;

@UseCase
@RequiredArgsConstructor
public class LoadBusService implements LoadBusUseCase {

    private final BusMapper busMapper;
    private final LoadBusPort loadBusPort;
    private final LoadRouteUseCase loadRouteUseCase;

    @Override
    public List<BusCommand> loadBusList() {
        List<BusCommand> busList = new ArrayList<>();
        for (BusJpaEntity bus : loadBusPort.loadBusList()) {
            Bus busDomainEntity = busMapper.mapToDomainEntity(bus);
            List<Route> routes = new ArrayList<>();
            for (BusRouteJpaEntity busRoute : bus.getRoutes()) {
                final var route = loadRouteUseCase.loadRouteById(
                        Company.CompanyId.of(bus.getCompany().getId()),
                        new Route.RouteId(busRoute.getRoute().getId())
                );

                routes.add(route);
            }

            busList.add(
                    new BusCommand(busDomainEntity.getId().getValue().getCompanyId(),
                            busDomainEntity.getBusNum(),
                            routes));
        }
        return busList;
    }

    @Override
    public BusCommand loadBusById(BusCommand busCommand) {
        BusJpaEntity busJpaEntity = loadBusPort.loadBusById(
                new Bus.BusId(busCommand.getCompanyId(), busCommand.getBusNum()));
        Bus bus = busMapper.mapToDomainEntity(busJpaEntity);

        List<Route> routes = new ArrayList<>();
        for (BusRouteJpaEntity busRoute : busJpaEntity.getRoutes()) {
            final var route = loadRouteUseCase.loadRouteById(
                    Company.CompanyId.of(busJpaEntity.getCompany().getId()),
                    new Route.RouteId(busRoute.getRoute().getId())
            );

            routes.add(route);
        }

        return new BusCommand(bus.getId().getValue().getCompanyId(), bus.getBusNum(), routes);
    }
}
