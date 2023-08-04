package org.comfort42.busking.application.port.inbound;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.comfort42.busking.application.domain.model.Report;
import org.comfort42.busking.application.domain.model.User;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class ReportCommand {
    private final Report.ReportId reportId;

    private final User.UserId userId;

    private final String description;

    private final LocalDateTime localDateTime;

    private final Double lng;

    private final Double lat;

    private final Long busNum;

    private final Long companyId;

    private final String reporter;

}
