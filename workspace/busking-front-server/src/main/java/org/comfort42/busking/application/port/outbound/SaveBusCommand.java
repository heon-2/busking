package org.comfort42.busking.application.port.outbound;

import org.comfort42.busking.application.domain.model.Bus;

public record SaveBusCommand(Bus.BusId busId) {
}
