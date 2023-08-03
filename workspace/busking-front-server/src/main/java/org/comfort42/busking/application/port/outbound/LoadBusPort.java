package org.comfort42.busking.application.port.outbound;

import org.comfort42.busking.application.domain.model.Bus;
import org.comfort42.busking.persistence.adapter.outbound.BusJpaEntity;

import java.util.List;

public interface LoadBusPort {
    List<BusJpaEntity> loadBusList();

    BusJpaEntity loadBusById(Bus.BusId bus);
}
