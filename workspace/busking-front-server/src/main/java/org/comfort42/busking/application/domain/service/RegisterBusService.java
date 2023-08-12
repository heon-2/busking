package org.comfort42.busking.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Bus;
import org.comfort42.busking.application.port.inbound.BusCommand;
import org.comfort42.busking.application.port.inbound.RegisterBusUseCase;
import org.comfort42.busking.application.port.outbound.SaveBusCommand;
import org.comfort42.busking.application.port.outbound.SaveBusPort;
import org.comfort42.busking.common.UseCase;

import java.util.ArrayList;

@UseCase
@RequiredArgsConstructor
public class RegisterBusService implements RegisterBusUseCase {

    private final SaveBusPort saveBusPort;

    @Override
    public void registerBus(final Bus.BusId busId) {
        saveBusPort.saveBus(new SaveBusCommand(busId));
    }
}
