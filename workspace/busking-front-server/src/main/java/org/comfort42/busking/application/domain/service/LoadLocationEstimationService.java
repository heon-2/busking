package org.comfort42.busking.application.domain.service;

import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.LocationEstimation;
import org.comfort42.busking.application.port.inbound.LoadLocationEstimationUseCase;
import org.comfort42.busking.application.port.outbound.LoadLocationEstimationPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class LoadLocationEstimationService implements LoadLocationEstimationUseCase {

    private final LoadLocationEstimationPort loadLocationEstimationPort;

    LoadLocationEstimationService(final LoadLocationEstimationPort loadLocationEstimationPort) {
        this.loadLocationEstimationPort = loadLocationEstimationPort;
    }

    @Override
    public LocationEstimation loadLocationEstimation(final Company.CompanyId companyId, final long busNo) {
        return loadLocationEstimationPort.loadLocationEstimation(companyId, busNo);
    }

}
