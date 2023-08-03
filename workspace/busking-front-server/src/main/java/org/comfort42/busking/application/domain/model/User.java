package org.comfort42.busking.application.domain.model;

import java.util.UUID;

public record User(
        UserId id,
        String username,
        String password,
        String realName,
        String email,
        String phoneNumber,
        Company.CompanyId companyId,
        UserRole role
) {
    public record UserId(UUID value) {
        public static UserId of(final UUID value) {
            return new UserId(value);
        }

        public static UserId of(final String value) {
            return new UserId(UUID.fromString(value));
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }
}
