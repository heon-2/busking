package org.comfort42.busking.application.port.outbound;

import org.comfort42.busking.application.domain.model.Station;
import org.comfort42.busking.persistence.adapter.outbound.CompanyJpaEntity;

public interface RegisterStationPort {

    void registerStation(Station station);
}
