package org.comfort42.busking.persistence.adapter.outbound;

import org.comfort42.busking.application.domain.model.Bus;
import org.comfort42.busking.application.domain.model.Route;
import org.springframework.stereotype.Component;

@Component
public class BusMapper {
    Bus mapToDomainEntity(
            BusJpaEntity bus
    ) {
        return Bus.withId(
                new Bus.BusId(bus.getId()),
                bus.getBusNum()
        );
    }
}
