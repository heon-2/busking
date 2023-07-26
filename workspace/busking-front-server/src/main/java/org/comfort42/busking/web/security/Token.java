package org.comfort42.busking.web.security;

import java.time.Instant;
import java.util.UUID;

record Token(UUID tokenId, Instant issuedAt, String accessToken, String refreshToken) {
}
