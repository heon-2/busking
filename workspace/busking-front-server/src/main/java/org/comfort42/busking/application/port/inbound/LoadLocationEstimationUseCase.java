package org.comfort42.busking.application.port.inbound;

import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.LocationEstimation;

public interface LoadLocationEstimationUseCase {
    LocationEstimation loadLocationEstimation(Company.CompanyId companyId, long busNo);
}
