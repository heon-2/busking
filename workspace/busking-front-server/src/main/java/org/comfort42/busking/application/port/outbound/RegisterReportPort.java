package org.comfort42.busking.application.port.outbound;

import org.comfort42.busking.application.domain.model.Report;
import org.comfort42.busking.persistence.adapter.outbound.BusJpaEntity;
import org.comfort42.busking.persistence.adapter.outbound.ReportJpaEntity;

public interface RegisterReportPort {

    void registerReport(Report report, BusJpaEntity bus);
}
