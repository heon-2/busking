package org.comfort42.busking.application.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Value
@RequiredArgsConstructor
@Getter @Setter
public class Company {

    private CompanyId id;

    @NonNull
    private final String name;

    @Value
    public static class CompanyId {
        private final Long value;
    }

    public static Company withId(CompanyId companyId,String name) {
        return new Company(companyId,name);
    }
}
