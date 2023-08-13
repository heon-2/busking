package org.comfort42.busking.persistence.adapter.outbound;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.comfort42.busking.application.domain.model.RouteDirection;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "route")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RouteJpaEntity {
    @Id
    @GeneratedValue
    @Column(name = "route_id")
    private Long id;

    private String name;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "geometry", length = 8192)
    private String geometry;

    @Convert(converter = RouteDirectionConverter.class)
    private RouteDirection direction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", insertable = false, updatable = false)
    private CompanyJpaEntity company;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "RouteAndStation",
            joinColumns = @JoinColumn(name = "route_id"),
            inverseJoinColumns = @JoinColumn(name = "station_id", insertable = false, updatable = false)
    )
    private List<StationJpaEntity> stations = new ArrayList<>();
}
