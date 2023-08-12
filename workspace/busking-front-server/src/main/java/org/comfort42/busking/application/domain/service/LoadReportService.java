package org.comfort42.busking.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Report;
import org.comfort42.busking.application.port.inbound.LoadReportUseCase;
import org.comfort42.busking.application.port.inbound.ReportCommand;
import org.comfort42.busking.application.port.outbound.LoadReportPort;
import org.comfort42.busking.common.UseCase;

import java.util.ArrayList;
import java.util.List;

@UseCase
@RequiredArgsConstructor
public class LoadReportService implements LoadReportUseCase {

    private final LoadReportPort loadReportPort;

    @Override
    public List<ReportCommand> loadReportList() {
        List<ReportCommand> reports = new ArrayList<>();
        for (Report report : loadReportPort.loadReportList()) {
            ReportCommand reportCommand = new ReportCommand(
                    report.getId(),
                    report.getReporter().id(),
                    report.getDescription(),
                    report.getCreatedAt(),
                    report.getLng(),
                    report.getLat(),
                    report.getBus().getId().no(),
                    report.getBus().getId().companyId().value(),
                    report.getReporter().username()
            );
            reports.add(reportCommand);
        }
        return reports;
    }

    @Override
    public ReportCommand loadReportById(Report.ReportId reportId) {
        Report report = loadReportPort.loadReportById(reportId);
        return new ReportCommand(
                report.getId(),
                report.getReporter().id(),
                report.getDescription(),
                report.getCreatedAt(),
                report.getLng(),
                report.getLat(),
                report.getBus().getId().no(),
                report.getBus().getId().companyId().value(),
                report.getReporter().username()
        );
    }
}
