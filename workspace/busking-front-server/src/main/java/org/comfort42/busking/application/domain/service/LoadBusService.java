package org.comfort42.busking.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.comfort42.busking.application.domain.model.Bus;
import org.comfort42.busking.application.port.inbound.BusCommand;
import org.comfort42.busking.application.port.inbound.LoadBusUseCase;
import org.comfort42.busking.common.UseCase;
import org.comfort42.busking.persistence.adapter.outbound.BusJpaEntity;
import org.comfort42.busking.persistence.adapter.outbound.BusMapper;
import org.comfort42.busking.persistence.adapter.outbound.LoadBusPersistenceAdapter;

import java.util.ArrayList;
import java.util.List;

@UseCase
@RequiredArgsConstructor
public class LoadBusService implements LoadBusUseCase {

    private final LoadBusPersistenceAdapter loadBusPersistenceAdapter;
    private final BusMapper busMapper;

    @Override
    public List<BusCommand> loadBusList() {
        List<BusCommand> busList = new ArrayList<>();
        for (BusJpaEntity bus : loadBusPersistenceAdapter.loadBusList()) {
            Bus busDomainEntity = busMapper.mapToDomainEntity(bus);
            busList.add(
                    new BusCommand(busDomainEntity.getId().getValue().getCompanyId(),
                            busDomainEntity.getBusNum()));
        }
        return busList;
    }

    @Override
    public BusCommand loadBusById(BusCommand busCommand) {
        Bus bus=busMapper.mapToDomainEntity(
                loadBusPersistenceAdapter.loadBusById(
                        new Bus.BusId(busCommand.getCompanyId(),
                                busCommand.getBusNum())));
        return new BusCommand(bus.getId().getValue().getCompanyId(),bus.getBusNum());
    }
}
