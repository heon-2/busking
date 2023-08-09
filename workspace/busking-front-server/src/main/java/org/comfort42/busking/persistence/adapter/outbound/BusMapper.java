package org.comfort42.busking.persistence.adapter.outbound;

import org.comfort42.busking.application.domain.model.Bus;
import org.comfort42.busking.application.domain.model.BusRoute;
import org.comfort42.busking.application.domain.model.Route;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BusMapper {

    private static final BusRouteMapper busRouteMapper=BusRouteMapper.getInstance();

    public Bus mapToDomainEntity(
            BusJpaEntity bus
    ) {
        List<BusRoute> busRoutes=new ArrayList<>();
        for(BusRouteJpaEntity busRouteJpaEntity:bus.getRoutes()){
            busRoutes.add(busRouteMapper.mapToDomainEntity(busRouteJpaEntity));
        }
        return Bus.withId(
                new Bus.BusId(bus.getId().getCompanyId(),bus.getBusNum()),
                bus.getId().getBusNum(),
                busRoutes
        );
    }
}
