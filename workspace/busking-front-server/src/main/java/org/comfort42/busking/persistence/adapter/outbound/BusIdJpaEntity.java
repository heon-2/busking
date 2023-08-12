package org.comfort42.busking.persistence.adapter.outbound;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@Embeddable
public class BusIdJpaEntity implements Serializable {
    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "bus_no")
    private Long no;

    public BusIdJpaEntity(Long companyId, Long busNum) {
        this.companyId = companyId;
        this.no = busNum;
    }

    // equals() 및 hashCode() 메서드 오버라이딩 (companyId와 busNum을 기준으로 비교)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusIdJpaEntity busIdJpaEntity = (BusIdJpaEntity) o;
        return Objects.equals(companyId, busIdJpaEntity.companyId) && Objects.equals(no, busIdJpaEntity.no);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, no);
    }
}
