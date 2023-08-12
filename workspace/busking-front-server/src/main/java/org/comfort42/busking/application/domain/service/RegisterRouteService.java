package org.comfort42.busking.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.Route;
import org.comfort42.busking.application.domain.model.Station;
import org.comfort42.busking.application.port.inbound.RegisterRouteCommand;
import org.comfort42.busking.application.port.inbound.RegisterRouteUseCase;
import org.comfort42.busking.application.port.outbound.LoadCompanyPort;
import org.comfort42.busking.application.port.outbound.LoadStationPort;
import org.comfort42.busking.application.port.outbound.SaveRoutePort;
import org.comfort42.busking.common.UseCase;
import org.comfort42.busking.persistence.adapter.outbound.SaveRouteCommand;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class RegisterRouteService implements RegisterRouteUseCase {

    private final LoadStationPort loadStationPort;
    private final SaveRoutePort saveRoutePort;

    @Override
    public void registerRoute(final RegisterRouteCommand cmd) {
        final var saveRouteCommand = new SaveRouteCommand(
                cmd.companyId(),
                cmd.routeName(),
                cmd.routeGeometry(),
                cmd.routeDirection(),
                cmd.stations()
        );
        saveRoutePort.registerRoute(saveRouteCommand);
    }
}
