package it.unicam.cs.idsflsm.municipalplatform.application.criterias.user;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.RoleRequest;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserRole;

import java.util.function.Predicate;
/**
 * Utility class providing various predicates for filtering RoleRequest objects based on different criteria.
 * In general, predicates on entity fields are optional filters
 */
public class RoleRequestCriteria {
    public static Predicate<RoleRequest> hasUsername(String username) {
        if (username != null && !username.isBlank()) {
            return roleRequest -> roleRequest.getUsername().contains(username);
        } else {
            return roleRequest -> true;
        }
    }
    public static Predicate<RoleRequest> hasRole(UserRole role) {
        if (role != null) {
            return roleRequest -> roleRequest.getRole().equals(role);
        } else {
            return roleRequest -> true;
        }
    }
    @SafeVarargs
    public static Predicate<RoleRequest> criteriaBuilder(Predicate<RoleRequest>... predicates) {
        Predicate<RoleRequest> result = user -> true;
        for (Predicate<RoleRequest> predicate : predicates) {
            result = result.and(predicate);
        }
        return result;
    }
}
