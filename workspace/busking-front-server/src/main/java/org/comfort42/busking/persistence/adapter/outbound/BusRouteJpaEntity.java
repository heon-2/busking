package org.comfort42.busking.persistence.adapter.outbound;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.comfort42.busking.application.domain.model.RouteDirection;

@Entity
@Table(name = "bus_route")
@Getter
@Setter
public class BusRouteJpaEntity {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "bus_id")
    @JoinColumns({
            @JoinColumn(name = "company_id"),
            @JoinColumn(name = "bus_id")
    })
    private BusJpaEntity bus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id")
    private RouteJpaEntity route;
}
