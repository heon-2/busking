package org.comfort42.busking.application.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
@RequiredArgsConstructor
@Getter @Setter
public class Company {

    private CompanyId id;

    private final String name;

    @Value
    public static class CompanyId {
        private final Long value;
    }
}
