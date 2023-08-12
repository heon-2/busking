package org.comfort42.busking.application.port.outbound;

import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.LocationEstimation;

public interface LoadLocationEstimationPort {
    LocationEstimation loadLocationEstimation(Company.CompanyId companyId, long busNo);
}
