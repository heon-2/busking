package org.comfort42.busking.application.domain.service;

import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.Token;
import org.comfort42.busking.application.domain.model.User;
import org.comfort42.busking.application.port.inbound.IssueTokenUseCase;
import org.comfort42.busking.application.port.outbound.SaveTokenPort;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
class IssueTokenService extends IssueTokenUseCase {
    private final SaveTokenPort saveTokenPort;

    IssueTokenService(final SaveTokenPort saveTokenPort) {
        this.saveTokenPort = saveTokenPort;
    }

    @Override
    public Optional<Token> issueTokenFor(final User.UserId subjectUserId, final Company.CompanyId subjectCompanyId, final Duration accessDuration, final Duration refreshDuration) {
        final Instant now = Instant.now();
        final Instant accessTokenExpiresAt = now.plus(accessDuration);
        final Instant refreshTokenExpiresAt = now.plus(refreshDuration);
        final Token.TokenSubject tokenSubject = new Token.TokenSubject(subjectUserId, subjectCompanyId);

        final Token token = new Token(Token.TokenId.of(UUID.randomUUID()), tokenSubject, now, accessTokenExpiresAt, refreshTokenExpiresAt);
        saveTokenPort.save(token);

        return Optional.of(token);
    }
}
