package org.comfort42.busking.application.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Value;

import java.util.List;

@Value
@RequiredArgsConstructor
@Getter
@Setter
public class Bus {

    private final BusId id;

    private final List<Route> routes;

    public record BusId(Company.CompanyId companyId, long no) {
        public static BusId of(Company.CompanyId companyId, long no) {
            return new BusId(companyId, no);
        }
    }
}
