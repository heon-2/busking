package org.comfort42.busking.persistence.adapter.outbound;

import org.comfort42.busking.application.domain.model.RealtimeBusState;

import java.util.List;

class RealtimeBusStateMapper {

    private static RealtimeBusStateMapper instance;

    static RealtimeBusStateMapper getInstance() {
        if (instance == null) {
            instance = new RealtimeBusStateMapper();
        }
        return instance;
    }

    RealtimeBusState mapToDomainEntity(final List<Object> resultSet) {
        return new RealtimeBusState(
                Long.parseLong((String) resultSet.get(0)),
                Double.parseDouble((String) resultSet.get(1)),
                Double.parseDouble((String) resultSet.get(2)),
                Long.parseLong((String) resultSet.get(3)),
                Double.parseDouble((String) resultSet.get(4)),
                Double.parseDouble((String) resultSet.get(5))
        );
    }

}
