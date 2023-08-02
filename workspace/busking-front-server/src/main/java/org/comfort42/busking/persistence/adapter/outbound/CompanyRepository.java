package org.comfort42.busking.persistence.adapter.outbound;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository  extends JpaRepository<CompanyJpaEntity, Long> {
}
