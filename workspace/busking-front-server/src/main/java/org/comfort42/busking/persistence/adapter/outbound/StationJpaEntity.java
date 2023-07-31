package org.comfort42.busking.persistence.adapter.outbound;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "station")
@Getter
public class StationJpaEntity {
    @Id
    @GeneratedValue
    @Column(name = "station_id")
    private Long id;

    @Length(max = 100)
    private String name;

    private Double lng;

    private Double lat;

    @OneToMany(mappedBy = "station")
    private List<RouteStationJpaEntity> routes = new ArrayList<>();
}
