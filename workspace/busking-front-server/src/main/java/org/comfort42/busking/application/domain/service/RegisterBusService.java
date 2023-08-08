package org.comfort42.busking.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Bus;
import org.comfort42.busking.application.port.inbound.BusCommand;
import org.comfort42.busking.application.port.inbound.RegisterBusUseCase;
import org.comfort42.busking.common.UseCase;
import org.comfort42.busking.persistence.adapter.outbound.SaveBusPersistenceAdapter;

import java.util.ArrayList;

@UseCase
@RequiredArgsConstructor
public class RegisterBusService implements RegisterBusUseCase {

    private final SaveBusPersistenceAdapter saveBusPersistenceAdapter;
    @Override
    public void registerBus(BusCommand bus) {
        Bus busDomainEntity=new Bus(
                new Bus.BusId(bus.getCompanyId(),bus.getBusNum()),
                bus.getBusNum(),
                new ArrayList<>()
        );
        saveBusPersistenceAdapter.save(busDomainEntity);
    }
}
