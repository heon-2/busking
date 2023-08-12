package org.comfort42.busking.persistence.adapter.outbound;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bus")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusJpaEntity {
    @EmbeddedId
    @Column(name = "bus_id")
    private BusIdJpaEntity id;

    @OneToMany(cascade = CascadeType.DETACH)
    @JoinTable(
            name = "BusAndRoute",
            joinColumns = {@JoinColumn(name = "company_id"), @JoinColumn(name = "bus_no")},
            inverseJoinColumns = @JoinColumn(name = "route_id", insertable = false, updatable = false)
    )
    private List<RouteJpaEntity> routes = new ArrayList<>();
}
