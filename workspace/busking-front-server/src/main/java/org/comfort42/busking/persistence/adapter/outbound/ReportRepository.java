package org.comfort42.busking.persistence.adapter.outbound;
//SaveFcmTokenRepository
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Report;
import org.comfort42.busking.application.port.outbound.LoadReportPort;
import org.comfort42.busking.application.port.outbound.RegisterReportPort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReportRepository implements RegisterReportPort, LoadReportPort {

    @PersistenceContext
    private final EntityManager em;

    private final ReportMapper reportMapper;
    private static final UserMapper userMapper=UserMapper.getInstance();

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

    @Override
    public List<Report> loadReportList() {
        List<ReportJpaEntity> reportJpaEntities=em.createQuery("select r from ReportJpaEntity r", ReportJpaEntity.class)
                .getResultList();
        List<Report> reports=new ArrayList<>();
        for(ReportJpaEntity reportJpaEntity: reportJpaEntities){
            reports.add(reportMapper.mapToDomainEntity(reportJpaEntity));
        }
        return reports;
    }

    @Override
    public Report loadReportById(Report.ReportId reportId) {
        return reportMapper.mapToDomainEntity(em.find(ReportJpaEntity.class,reportId.getValue()));
    }
}
