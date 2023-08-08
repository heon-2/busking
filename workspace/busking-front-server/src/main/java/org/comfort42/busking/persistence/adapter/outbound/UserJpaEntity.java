package org.comfort42.busking.persistence.adapter.outbound;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.comfort42.busking.application.domain.model.UserRole;
import org.hibernate.type.descriptor.converter.spi.EnumValueConverter;

import java.util.UUID;

@Entity
@Table(name = "user",indexes = @Index(name = "i_realName",columnList = "realName"))
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
class UserJpaEntity {

    @Column
    @Id
    private UUID id = null;

    @Column(unique = true)
    private String username = null;

    @Column
    private String password = null;

    @Column
    private String realName = null;

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
