package org.comfort42.busking.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.port.inbound.RegisterRouteUseCase;
import org.comfort42.busking.application.port.inbound.RouteCommand;
import org.comfort42.busking.common.UseCase;

@UseCase
@RequiredArgsConstructor
public class RegisterRouteService  implements RegisterRouteUseCase {
    @Override
    public void registerRoute(RouteCommand registerCommand) {

    }
}
