package org.comfort42.busking.application.port.inbound;

import java.util.List;

public interface LoadBusUseCase {
    List<BusCommand> loadBusList();

    BusCommand loadBusById(BusCommand busCommand);
}
