package org.comfort42.busking.persistence.adapter.outbound;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "company")
@Getter @Setter
public class CompanyJpaEntity {
    @Id
    @GeneratedValue
    @Column(name="company_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "company")
    private List<BusJpaEntity> buses =new ArrayList<>();
}
