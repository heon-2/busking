package org.comfort42.busking.persistence.adapter.outbound;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="route_station")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RouteStationJpaEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id")
    private StationJpaEntity station;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id")
    private RouteJpaEntity route;
}
