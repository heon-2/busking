package org.comfort42.busking.persistence.adapter.outbound;

import jakarta.persistence.*;

@Entity
@Table(name = "company")
public class CompanyJpaEntity {
    @Id
    @GeneratedValue
    @Column(name="company_id")
    private Long id;

    private String name;

//    @OneToMany(mappedBy = "company")
//    private List<Bus> buses =new ArrayList<>();
}
