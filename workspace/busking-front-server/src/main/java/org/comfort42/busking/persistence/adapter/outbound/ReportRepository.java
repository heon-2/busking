package org.comfort42.busking.persistence.adapter.outbound;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Report;
import org.comfort42.busking.application.port.outbound.RegisterReportPort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ReportRepository implements RegisterReportPort {

    @PersistenceContext
    private EntityManager em;

    private ReportMapper reportMapper;
    private UserMapper userMapper;
    private BusMapper busMapper;

    @Override
    @Transactional
    public void registerReport(Report report,BusJpaEntity bus) {
        ReportJpaEntity reportJpaEntity=new ReportJpaEntity();
        reportJpaEntity.setReporter(userMapper.mapToJpaEntity(report.getReporter()));
        reportJpaEntity.setDescription(report.getDescription());
        reportJpaEntity.setLng(report.getLng());
        reportJpaEntity.setLat(report.getLat());
        reportJpaEntity.setBus(bus);
        em.persist(reportJpaEntity);
    }
}
