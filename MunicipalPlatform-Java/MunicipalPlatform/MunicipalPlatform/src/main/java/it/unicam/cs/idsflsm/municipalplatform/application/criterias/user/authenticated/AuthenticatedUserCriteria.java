package it.unicam.cs.idsflsm.municipalplatform.application.criterias.user.authenticated;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserRole;

import java.util.UUID;
import java.util.function.Predicate;
/**
 * Utility class providing various predicates for filtering AuthenticatedUser objects based on different criteria.
 * In general, predicates on entity fields are optional filters
 */
public class AuthenticatedUserCriteria {
    public static Predicate<AuthenticatedUser> hasId(UUID id) {
        if (id != null) {
            return authenticatedUser -> authenticatedUser.getId().equals(id);
        } else {
            return authenticatedUser -> true;
        }
    }
    public static Predicate<AuthenticatedUser> hasUsername(String username) {
        if (username != null && !username.isBlank()) {
            return authenticatedUser -> authenticatedUser.getUsername().contains(username);
        } else {
            return authenticatedUser -> true;
        }
    }
    public static Predicate<AuthenticatedUser> hasName(String name) {
        if (name != null && !name.isBlank()) {
            return authenticatedUser -> authenticatedUser.getName().toLowerCase().contains(name.toLowerCase());
        } else {
            return authenticatedUser -> true;
        }
    }
    public static Predicate<AuthenticatedUser> hasSurname(String surname) {
        if (surname != null && !surname.isBlank()) {
            return authenticatedUser -> authenticatedUser.getSurname().toLowerCase().contains(surname.toLowerCase());
        } else {
            return authenticatedUser -> true;
        }
    }
    public static Predicate<AuthenticatedUser> hasRole(UserRole role) {
        if (role != null) {
            return authenticatedUser -> authenticatedUser.getRole().equals(role);
        } else {
            return authenticatedUser -> true;
        }
    }
    @SafeVarargs
    public static Predicate<AuthenticatedUser> criteriaBuilder(Predicate<AuthenticatedUser>... predicates) {
        Predicate<AuthenticatedUser> result = user -> true;
        for (Predicate<AuthenticatedUser> predicate : predicates) {
            result = result.and(predicate);
        }
        return result;
    }
}

