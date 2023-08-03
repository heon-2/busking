package org.comfort42.busking.persistence.adapter.outbound;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.comfort42.busking.application.domain.model.UserRole;
import org.hibernate.type.descriptor.converter.spi.EnumValueConverter;

@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
class UserJpaEntity {

    @Column
    @Id
    private String id = null;

    @Column
    private String password = null;

    @Column
    private String name = null;

    @Column
    private String email = null;

    @Column
    private String phoneNumber = null;

    @Column(name = "company_id")
    private Long companyId = null;

    @Column
    @Convert(converter = UserRoleConverter.class)
    private UserRole role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", insertable = false, updatable = false)
    private CompanyJpaEntity company = null;

}
