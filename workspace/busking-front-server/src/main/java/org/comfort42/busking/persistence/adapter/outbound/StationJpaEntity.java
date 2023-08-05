package org.comfort42.busking.persistence.adapter.outbound;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "station")
@Getter
@Setter
public class StationJpaEntity {
    @Id
    @GeneratedValue
    @Column(name = "station_id")
    private Long id;

    @Length(max = 100)
    private String name;

    private Double lng;

    private Double lat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", insertable = false, updatable = false)
    private CompanyJpaEntity company;

    @OneToMany(mappedBy = "station",cascade = CascadeType.REMOVE)
    private List<RouteStationJpaEntity> routes = new ArrayList<>();
}
