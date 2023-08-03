package org.comfort42.busking.persistence.adapter.outbound;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Bus;
import org.comfort42.busking.application.port.outbound.LoadBusPort;
import org.comfort42.busking.common.PersistenceAdapter;

import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
public class LoadBusPersistenceAdapter {
    private final LoadBusPort loadBusPort;

    public List<BusJpaEntity> loadBusList(){
        return loadBusPort.loadBusList();
    }

    public BusJpaEntity loadBusById(Bus.BusId busId){return loadBusPort.loadBusById(busId);}
}
