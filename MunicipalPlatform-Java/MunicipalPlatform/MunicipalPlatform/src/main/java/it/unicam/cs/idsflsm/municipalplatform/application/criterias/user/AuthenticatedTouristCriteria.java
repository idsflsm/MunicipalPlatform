package it.unicam.cs.idsflsm.municipalplatform.application.criterias.user;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contest;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedTourist;

import java.util.function.Predicate;

public class AuthenticatedTouristCriteria {
    public static Predicate<AuthenticatedTourist> hasUsername(String username) {
        if (!username.isBlank()) {
            return authenticatedUser -> authenticatedUser.getUsername().toLowerCase().contains(username.toLowerCase());
        } else {
            return authenticatedUser -> true;
        }
    }
    public static Predicate<AuthenticatedTourist> hasName(String name) {
        if (!name.isBlank()) {
            return authenticatedUser -> authenticatedUser.getName().toLowerCase().contains(name.toLowerCase());
        } else {
            return authenticatedUser -> true;
        }
    }
    public static Predicate<AuthenticatedTourist> hasSurname(String surname) {
        if (!surname.isBlank()) {
            return authenticatedUser -> authenticatedUser.getSurname().toLowerCase().contains(surname.toLowerCase());
        } else {
            return authenticatedUser -> true;
        }
    }
    @SafeVarargs
    public static Predicate<AuthenticatedTourist> criteriaBuilder(Predicate<AuthenticatedTourist>... predicates) {
        Predicate<AuthenticatedTourist> result = contest -> true;
        for (Predicate<AuthenticatedTourist> predicate : predicates) {
            result = result.and(predicate);
        }
        return result;
    }
}
