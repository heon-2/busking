package org.comfort42.busking.persistence.adapter.outbound;

import org.comfort42.busking.application.domain.model.Bus;
import org.comfort42.busking.application.domain.model.Report;
import org.springframework.stereotype.Component;

@Component
public class ReportMapper {
    UserMapper userMapper=new UserMapper();
    BusMapper busMapper=new BusMapper();
    Report mapToDomainEntity(
            ReportJpaEntity report
    ) {
        return Report.withId(
                new Report.ReportId(report.getId()),
                userMapper.mapJpaEntityToDomainModel(report.getReporter()),
                report.getDescription(),
                report.getCreated_at(),
                report.getLat(),
                report.getLng(),
                busMapper.mapToDomainEntity(report.getBus())
        );
    }
}
