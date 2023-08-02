package org.comfort42.busking.persistence.adapter.outbound;

import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class BusPK implements Serializable {
    @JoinColumn(name = "company_id")
    private Long companyId;

    @JoinColumn(name="bus_num")
    private Long busNum;

    // 기본 생성자 필요 (기본 생성자를 명시적으로 추가)
    public BusPK() {}

    public BusPK(Long companyId, Long busNum) {
        this.companyId = companyId;
        this.busNum = busNum;
    }

    // equals() 및 hashCode() 메서드 오버라이딩 (companyId와 busNum을 기준으로 비교)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusPK busPK = (BusPK) o;
        return Objects.equals(companyId, busPK.companyId) &&
                Objects.equals(busNum, busPK.busNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, busNum);
    }
}
