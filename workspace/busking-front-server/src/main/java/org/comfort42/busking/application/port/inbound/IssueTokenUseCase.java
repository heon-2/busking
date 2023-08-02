package org.comfort42.busking.application.port.inbound;

import org.comfort42.busking.application.domain.model.Token;
import org.comfort42.busking.application.domain.model.User;

import java.time.Duration;
import java.util.Optional;

public abstract class IssueTokenUseCase {
    public abstract Optional<Token> issueTokenFor(User.UserId subject, Duration accessDuration, Duration refreshDuration);

    public Optional<Token> issueTokenFor(final User.UserId subject, final Duration accessDuration) {
        return issueTokenFor(subject, accessDuration, accessDuration);
    }
}
