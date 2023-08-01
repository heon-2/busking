package org.comfort42.busking.persistence.adapter.outbound;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<ReportJpaEntity,Long> {
}
