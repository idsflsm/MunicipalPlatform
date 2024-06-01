package it.unicam.cs.idsflsm.municipalplatform.application.factories.user;
import it.unicam.cs.idsflsm.municipalplatform.application.builders.user.*;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.*;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserRole;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
@Component
@NoArgsConstructor
public class AuthenticatedUserBuilderFactory {
    private final Map<UserRole, AuthenticatedUserBuilder> builderMap = new HashMap<>(Map.of(
            UserRole.AUTHENTICATED_TOURIST, new AuthenticatedTouristBuilder(),
            UserRole.CONTRIBUTOR, new ContributorBuilder(),
            UserRole.AUTHORIZED_CONTRIBUTOR, new AuthorizedContributorBuilder(),
            UserRole.ANIMATOR, new AnimatorBuilder(),
            UserRole.CURATOR, new CuratorBuilder(),
            UserRole.ADMINISTRATOR, new AdministratorBuilder()
    ));
    public AuthenticatedUserBuilder createAuthenticatedUserBuilder(UserRole role) {
        AuthenticatedUserBuilder authenticatedUserBuilder = builderMap.get(role);
        if (authenticatedUserBuilder == null) {
            throw new IllegalArgumentException("Unsupported role: " + role);
        }
        return authenticatedUserBuilder;
    }
}
