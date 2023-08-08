package org.comfort42.busking.persistence.adapter.outbound;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ViewUserRepository extends JpaRepository<UserJpaEntity, UUID> {
    @Query(value = " select * from user where company_id= :companyId order by real_name limit :page,20;", nativeQuery = true)
    List<UserJpaEntity> findByCompanyId(@Param(value = "companyId") long companyId,
                                        @Param(value = "page") long page);

    Long countByCompanyId(long companyId);
}
