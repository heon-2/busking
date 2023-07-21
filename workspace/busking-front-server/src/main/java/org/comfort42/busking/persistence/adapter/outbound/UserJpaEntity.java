package org.comfort42.busking.persistence.adapter.outbound;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
class UserJpaEntity {

    @Column
    @Id
    @Getter
    @Setter
    private String id;

    @Column
    @Getter
    @Setter
    private String password;

}
