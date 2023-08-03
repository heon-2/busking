package org.comfort42.busking.application.port.outbound;

import org.comfort42.busking.application.domain.model.Bus;
import org.comfort42.busking.persistence.adapter.outbound.BusJpaEntity;

public interface RegisterBusPort {
    BusJpaEntity registerBus(Bus bus);
}
