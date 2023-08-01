package org.comfort42.busking.persistence.adapter.outbound;

import jakarta.persistence.*;
import lombok.Getter;
import org.comfort42.busking.application.domain.model.BusRoute;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bus")
@Getter
public class BusJpaEntity {
    @EmbeddedId
    @Column(name="bus_id")
    private BusPK id;

    @Column(name="bus_num")
    private Long busNum;

    @OneToMany(mappedBy = "bus",cascade = CascadeType.REMOVE)
    private List<BusRouteJpaEntity> routes = new ArrayList<>();
}
