package org.comfort42.busking.application.port.outbound;

import org.comfort42.busking.application.domain.model.Station;

public interface RegisterStationPort {

    void registerStation(Station station);
}
