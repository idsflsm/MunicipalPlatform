package it.unicam.cs.idsflsm.municipalplatform.application.criterias.contest;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contest;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;

import java.util.UUID;
import java.util.function.Predicate;
/**
 * Utility class providing various predicates for filtering Contest objects based on different criteria.
 * In general, predicates on entity fields are optional filters
 */
public class ContestCriteria {
    public static Predicate<Contest> hasId(UUID id) {
        if (id != null) {
            return contest -> contest.getId().equals(id);
        } else {
            return contest -> true;
        }
    }
    public static Predicate<Contest> hasName(String name) {
        if (name != null && !name.isBlank()) {
            return contest -> contest.getName().toLowerCase().contains(name.toLowerCase());
        } else {
            return contest -> true;
        }
    }
    public static Predicate<Contest> hasDescription(String description) {
        if (description != null && !description.isBlank()) {
            return contest -> contest.getDescription().toLowerCase().contains(description.toLowerCase());
        } else {
            return contest -> true;
        }
    }
    public static Predicate<Contest> hasAuthor(String author) {
        if (author != null && !author.isBlank()) {
            return contest -> contest.getAuthor().toLowerCase().contains(author.toLowerCase());
        } else {
            return contest -> true;
        }
    }
    public static Predicate<Contest> hasCreationDate(Date creationDate) {
        if (creationDate != null) {
            return contest -> contest.getCreationDate().equals(creationDate);
        } else {
            return contest -> true;
        }
    }
    public static Predicate<Contest> hasExpiryDate(Date expiryDate) {
        if (expiryDate != null) {
            return contest -> contest.getExpiryDate().equals(expiryDate);
        } else {
            return contest -> true;
        }
    }
    public static Predicate<Contest> hasWinner(boolean hasWinner) {
        return contest -> contest.isHasWinner() == hasWinner;
    }
    @SafeVarargs
    public static Predicate<Contest> criteriaBuilder(Predicate<Contest>... predicates) {
        Predicate<Contest> result = contest -> true;
        for (Predicate<Contest> predicate : predicates) {
            result = result.and(predicate);
        }
        return result;
    }
}
