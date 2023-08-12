package org.comfort42.busking.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Bus;
import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.Report;
import org.comfort42.busking.application.domain.model.User;
import org.comfort42.busking.application.port.inbound.RegisterReportUseCase;
import org.comfort42.busking.application.port.inbound.ReportCommand;
import org.comfort42.busking.application.port.outbound.LoadBusPort;
import org.comfort42.busking.application.port.outbound.LoadUserPort;
import org.comfort42.busking.application.port.outbound.SaveReportPort;
import org.comfort42.busking.common.UseCase;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class RegisterReportService implements RegisterReportUseCase {

    private final SaveReportPort registerReportPort;
    private final LoadUserPort loadUserPort;
    private final LoadBusPort loadBusPort;


    @Override
    public void registerReport(ReportCommand cmd) {
        final var companyId = Company.CompanyId.of(cmd.getCompanyId());

        Optional<User> reporter = loadUserPort.loadUserById(cmd.getUserId());
        final Bus bus = loadBusPort.loadBusById(new Bus.BusId(companyId, cmd.getBusNum()));

        Report report = Report.withId(
                null,
                reporter.orElse(null),
                cmd.getDescription(),
                cmd.getLocalDateTime(),
                cmd.getLng(),
                cmd.getLat(),
                bus
        );
        registerReportPort.saveReport(report);
    }
}
