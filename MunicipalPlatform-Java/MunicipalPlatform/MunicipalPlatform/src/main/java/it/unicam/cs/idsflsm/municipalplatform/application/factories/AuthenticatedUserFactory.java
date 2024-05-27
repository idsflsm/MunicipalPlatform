package it.unicam.cs.idsflsm.municipalplatform.application.factories;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.*;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserRole;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
public class AuthenticatedUserFactory {
    private static final Map<UserRole, Supplier<AuthenticatedUser>> userMap = new HashMap<>();

    static {
        userMap.put(UserRole.AUTHENTICATED_TOURIST, AuthenticatedTourist::new);
        userMap.put(UserRole.CONTRIBUTOR, Contributor::new);
        userMap.put(UserRole.AUTHORIZED_CONTRIBUTOR, AuthorizedContributor::new);
        userMap.put(UserRole.ANIMATOR, Animator::new);
        userMap.put(UserRole.CURATOR, Curator::new);
        userMap.put(UserRole.ADMINISTRATOR, Administrator::new);
    }

    public static AuthenticatedUser createUser(UserRole role) {
        Supplier<AuthenticatedUser> userSupplier = userMap.get(role);
        if (userSupplier == null) {
            throw new IllegalArgumentException("Unsupported role: " + role);
        }
        return userSupplier.get();
    }
}