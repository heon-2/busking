package org.comfort42.busking.application.port.outbound;

import org.comfort42.busking.persistence.adapter.outbound.BusJpaEntity;

public interface SaveBusPort {
    void saveBus(SaveBusCommand cmd);
}
