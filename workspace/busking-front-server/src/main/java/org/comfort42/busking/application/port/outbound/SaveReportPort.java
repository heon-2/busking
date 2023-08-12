package org.comfort42.busking.application.port.outbound;

import org.comfort42.busking.application.domain.model.Report;

public interface SaveReportPort {
    void saveReport(Report report);
}
