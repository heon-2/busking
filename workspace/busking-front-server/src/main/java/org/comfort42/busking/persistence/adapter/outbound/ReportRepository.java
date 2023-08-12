package org.comfort42.busking.persistence.adapter.outbound;
//SaveFcmTokenRepository

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Report;
import org.comfort42.busking.application.port.outbound.LoadReportPort;
import org.comfort42.busking.application.port.outbound.SaveReportPort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReportRepository implements SaveReportPort, LoadReportPort {
    private static final UserMapper userMapper = UserMapper.getInstance();
    private static final BusMapper busMapper = BusMapper.getInstance();

    @PersistenceContext
    private final EntityManager em;

    private final ReportMapper reportMapper;

    @Override
    @Transactional
    public void saveReport(final Report report) {
        final var reportJpaEntity = new ReportJpaEntity(
                null,
                userMapper.mapToJpaEntity(report.getReporter()),
                report.getDescription(),
                report.getCreatedAt(),
                report.getLng(),
                report.getLat(),
                busMapper.mapToJpaEntity(report.getBus())
        );
        em.persist(reportJpaEntity);
    }

    @Override
    public List<Report> loadReportList() {
        List<ReportJpaEntity> reportJpaEntities = em.createQuery("select r from ReportJpaEntity r", ReportJpaEntity.class)
                .getResultList();
        List<Report> reports = new ArrayList<>();
        for (ReportJpaEntity reportJpaEntity : reportJpaEntities) {
            reports.add(reportMapper.mapToDomainEntity(reportJpaEntity));
        }
        return reports;
    }

    @Override
    public Report loadReportById(Report.ReportId reportId) {
        return reportMapper.mapToDomainEntity(em.find(ReportJpaEntity.class, reportId.getValue()));
    }
}
