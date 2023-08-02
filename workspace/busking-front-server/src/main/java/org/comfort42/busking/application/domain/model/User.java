package org.comfort42.busking.application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.EnumSet;

public record User(
        UserId id,
        String password,
        String email,
        String phone,
        EnumSet<UserRole> roles
) {
    public static class UserId {
        private final String value;

        private UserId(final String value) {
            this.value = value;
        }

        public static UserId of(final String value) {
            return new UserId(value);
        }

        @Override
        public String toString() {
            return value;
        }
    }
}
