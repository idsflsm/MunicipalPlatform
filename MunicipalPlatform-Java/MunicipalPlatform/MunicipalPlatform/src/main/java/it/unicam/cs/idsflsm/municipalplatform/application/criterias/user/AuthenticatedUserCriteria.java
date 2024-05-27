package it.unicam.cs.idsflsm.municipalplatform.application.criterias.user;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;

import java.util.function.Predicate;

public class AuthenticatedUserCriteria {
    public static Predicate<AuthenticatedUser> hasUsername(String username) {
        if (!username.isBlank()) {
            return authenticatedUser -> authenticatedUser.getUsername().toLowerCase().contains(username.toLowerCase());
        } else {
            return authenticatedUser -> true;
        }
    }
    public static Predicate<AuthenticatedUser> hasName(String name) {
        if (!name.isBlank()) {
            return authenticatedUser -> authenticatedUser.getName().toLowerCase().contains(name.toLowerCase());
        } else {
            return authenticatedUser -> true;
        }
    }
    public static Predicate<AuthenticatedUser> hasSurname(String surname) {
        if (!surname.isBlank()) {
            return authenticatedUser -> authenticatedUser.getSurname().toLowerCase().contains(surname.toLowerCase());
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

