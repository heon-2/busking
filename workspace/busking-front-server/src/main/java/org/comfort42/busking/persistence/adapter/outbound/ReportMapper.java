package org.comfort42.busking.persistence.adapter.outbound;

import org.comfort42.busking.application.domain.model.Report;
import org.springframework.stereotype.Component;

@Component
public class ReportMapper {
    final static UserMapper userMapper = UserMapper.getInstance();
    BusMapper busMapper = new BusMapper();

    Report mapToDomainEntity(
            ReportJpaEntity report
    ) {
        return Report.withId(
                new Report.ReportId(report.getId()),
                userMapper.mapToDomainEntity(report.getReporter()),
                report.getDescription(),
                report.getCreated_at(),
                report.getLng(),
                report.getLat(),
                busMapper.mapToDomainEntity(report.getBus())
        );
    }
}
