package org.comfort42.busking.persistence.adapter.outbound;

import jakarta.persistence.*;
import lombok.Getter;
import org.comfort42.busking.application.domain.model.BusRoute;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "route")
@Getter
public class RouteJpaEntity {
    @Id
    @GeneratedValue
    @Column(name = "route_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "route",cascade = CascadeType.REMOVE)
    private List<BusRouteJpaEntity> buses = new ArrayList<>();

    @OneToMany(mappedBy = "route",cascade = CascadeType.REMOVE)
    private List<RouteStationJpaEntity> stations = new ArrayList<>();
}
