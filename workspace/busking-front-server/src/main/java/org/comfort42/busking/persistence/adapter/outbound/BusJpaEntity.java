package org.comfort42.busking.persistence.adapter.outbound;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.comfort42.busking.application.domain.model.BusRoute;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bus")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusJpaEntity {
    @EmbeddedId
    @Column(name="bus_id")
    private BusPK id;

//    @Column(name="bus_num")
//    private Long busNum;

    // setBusNum 메서드 추가
    public void setBusNum(Long busNum) {
        this.id.setBusNum(busNum);
    }

    @OneToMany(mappedBy = "bus",cascade = CascadeType.REMOVE)
    private List<BusRouteJpaEntity> routes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "company")
    private CompanyJpaEntity company;

    public long getBusNum() {
        return this.id.getBusNum();
    }
}
