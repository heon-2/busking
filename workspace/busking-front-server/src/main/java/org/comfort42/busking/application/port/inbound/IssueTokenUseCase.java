package org.comfort42.busking.application.port.inbound;

import org.comfort42.busking.application.domain.model.Company;
import org.comfort42.busking.application.domain.model.Token;
import org.comfort42.busking.application.domain.model.User;

import java.time.Duration;
import java.util.Optional;

public abstract class IssueTokenUseCase {
    public abstract Optional<Token> issueTokenFor(
            User.UserId subjectUserId,
            Company.CompanyId subjectCompanyId,
            Duration accessDuration,
            Duration refreshDuration);

    public Optional<Token> issueTokenFor(final User.UserId subjectUserId, final Company.CompanyId subjectCompanyId, final Duration accessDuration) {
        return issueTokenFor(subjectUserId, subjectCompanyId, accessDuration, accessDuration);
    }
}
