package it.unicam.cs.idsflsm.municipalplatform.application.factories.user.authenticated;
import it.unicam.cs.idsflsm.municipalplatform.application.builders.user.authenticated.*;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserRole;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
/**
 * Builder factory class for the creation of different types of AuthenticatedUser builders
 */
@Component
@NoArgsConstructor
public class AuthenticatedUserBuilderFactory {
    /**
     * The map storing the relationship between user roles and their corresponding builder instances
     */
    private final Map<UserRole, AuthenticatedUserBuilder> builderMap = new HashMap<>(Map.of(
            UserRole.AUTHENTICATED_TOURIST, new AuthenticatedTouristBuilder(),
            UserRole.CONTRIBUTOR, new ContributorBuilder(),
            UserRole.AUTHORIZED_CONTRIBUTOR, new AuthorizedContributorBuilder(),
            UserRole.ANIMATOR, new AnimatorBuilder(),
            UserRole.CURATOR, new CuratorBuilder(),
            UserRole.ADMINISTRATOR, new AdministratorBuilder()
    ));
    /**
     * Method for creating an AuthenticatedUser builder based on the provided user role
     * @param role the user role determining the type of builder to be created
     * @return an AuthenticatedUser builder corresponding to the user role
     * @throws IllegalArgumentException if the provided role is not supported
     */
    public AuthenticatedUserBuilder createAuthenticatedUserBuilder(UserRole role) throws IllegalArgumentException {
        AuthenticatedUserBuilder authenticatedUserBuilder = builderMap.get(role);
        if (authenticatedUserBuilder == null) {
            throw new IllegalArgumentException("Unsupported role: " + role);
        }
        return authenticatedUserBuilder;
    }
}
