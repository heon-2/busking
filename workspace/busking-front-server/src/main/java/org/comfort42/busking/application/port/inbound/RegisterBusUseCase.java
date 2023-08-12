package org.comfort42.busking.application.port.inbound;

import org.comfort42.busking.application.domain.model.Bus;

public interface RegisterBusUseCase {
    void registerBus(Bus.BusId bus);
}
