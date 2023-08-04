package org.comfort42.busking.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Bus;
import org.comfort42.busking.application.domain.model.Report;
import org.comfort42.busking.application.domain.model.User;
import org.comfort42.busking.application.port.inbound.RegisterReportUseCase;
import org.comfort42.busking.application.port.inbound.ReportCommand;
import org.comfort42.busking.application.port.outbound.LoadBusPort;
import org.comfort42.busking.application.port.outbound.LoadUserPort;
import org.comfort42.busking.application.port.outbound.RegisterReportPort;
import org.comfort42.busking.common.UseCase;
import org.comfort42.busking.persistence.adapter.outbound.*;
import org.comfort42.busking.web.security.UserMapper;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class RegisterReportService implements RegisterReportUseCase {

    private final RegisterReportPort registerReportPort;
    private final LoadUserPort loadUserPort;
    private final LoadBusPort loadBusPort;


    @Override
    public void registerReport(ReportCommand reportCommand) {
        Optional<User> reporter=loadUserPort.loadUserById(reportCommand.getUserId().toString());
        BusJpaEntity busJpaEntity=loadBusPort.loadBusById(new Bus.BusId(reportCommand.getCompanyId(),reportCommand.getBusNum()));
        Report report = Report.withId(
                null,
                reporter.orElse(null),
                reportCommand.getDescription(),
                reportCommand.getLocalDateTime(),
                reportCommand.getLng(),
                reportCommand.getLat(),
                null
                );
        registerReportPort.registerReport(report,busJpaEntity);
    }
}
