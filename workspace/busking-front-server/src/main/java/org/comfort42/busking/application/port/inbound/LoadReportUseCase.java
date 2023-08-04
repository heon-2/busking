package org.comfort42.busking.application.port.inbound;


import org.comfort42.busking.application.domain.model.Report;
import org.comfort42.busking.application.port.inbound.ReportCommand;

import java.util.List;

public interface LoadReportUseCase {

    List<ReportCommand> loadReportList();

    ReportCommand loadReportById(Report.ReportId reportId);
}
