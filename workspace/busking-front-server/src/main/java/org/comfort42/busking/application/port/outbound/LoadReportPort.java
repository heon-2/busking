package org.comfort42.busking.application.port.outbound;

import org.comfort42.busking.application.domain.model.Report;
import java.util.List;

public interface LoadReportPort {

    List<Report> loadReportList();

    Report loadReportById(Report.ReportId reportId);
}
