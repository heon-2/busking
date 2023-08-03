package org.comfort42.busking.application.domain.model;

import lombok.*;

public record Company(@Getter CompanyId id, @Getter String name) {

    public record CompanyId(long value) {
        public static CompanyId of(final long value) {
            return new CompanyId(value);
        }

        public static CompanyId of(final String value) {
            return new CompanyId(Long.parseLong(value));
        }

    }

    public static Company withId(final CompanyId companyId, final String name) {
        return new Company(companyId, name);
    }

}
