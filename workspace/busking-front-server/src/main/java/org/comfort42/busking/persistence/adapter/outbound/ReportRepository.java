package org.comfort42.busking.persistence.adapter.outbound;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.comfort42.busking.application.port.outbound.RegisterReportPort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ReportRepository implements RegisterReportPort {

    @PersistenceContext
    private EntityManager em;
}
