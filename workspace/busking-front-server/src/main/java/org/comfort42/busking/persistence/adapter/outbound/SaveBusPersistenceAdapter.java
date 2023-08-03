package org.comfort42.busking.persistence.adapter.outbound;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Bus;
import org.comfort42.busking.application.port.outbound.RegisterBusPort;
import org.comfort42.busking.common.PersistenceAdapter;

@PersistenceAdapter
@RequiredArgsConstructor
public class SaveBusPersistenceAdapter {
    private final RegisterBusPort registerBusPort;

    public void save(Bus bus){
        registerBusPort.registerBus(bus);
    }
}
