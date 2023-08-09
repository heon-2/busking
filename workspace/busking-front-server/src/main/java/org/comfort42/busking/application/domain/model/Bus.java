package org.comfort42.busking.application.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Value;
import org.comfort42.busking.persistence.adapter.outbound.BusPK;

import java.util.ArrayList;
import java.util.List;

@Value
@RequiredArgsConstructor
@Getter @Setter
public class Bus {

    private final BusId id;

    private final Long busNum;

    private final List<BusRoute> routes;

    @Value
    public static class BusId{
        private final BusPK value;

        public BusId(Long companyId,Long busNum){this.value=new BusPK(companyId,busNum);}

        public BusId(BusPK busPK){this.value=busPK;}
    }

    public static Bus withId(BusId busId,
                             Long busNum,
                             List<BusRoute> routes
    ) {
        return new Bus(busId,busNum,routes);
    }
}
