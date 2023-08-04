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

        @Override
        public String toString() {
            return Long.toString(value);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CompanyId && ((CompanyId) obj).value == value;
        }

        @Override
        public int hashCode() {
            return Long.hashCode(value);
        }
    }

    public static Company withId(final CompanyId companyId, final String name) {
        return new Company(companyId, name);
    }

}
