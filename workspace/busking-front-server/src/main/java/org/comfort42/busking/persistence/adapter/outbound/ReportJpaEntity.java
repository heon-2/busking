package org.comfort42.busking.persistence.adapter.outbound;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Entity
@Table(name = "report")
@Getter
public class ReportJpaEntity {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserJpaEntity reporter;

    @Length(max = 512)
    private String description;

    @CreationTimestamp
    private LocalDateTime created_at = LocalDateTime.now();

    private Double lng;

    private Double lat;

    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name= "bus_id")
    @JoinColumns({
            @JoinColumn(name="company_id"),
            @JoinColumn(name="bus_id")
    })
    private BusJpaEntity bus;
}
