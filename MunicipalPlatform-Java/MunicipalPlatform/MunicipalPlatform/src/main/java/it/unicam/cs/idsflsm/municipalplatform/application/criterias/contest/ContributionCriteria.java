package it.unicam.cs.idsflsm.municipalplatform.application.criterias.contest;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contest;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contribution;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContestResult;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Coordinates;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;

import java.util.UUID;
import java.util.function.Predicate;
/**
 * Utility class providing various predicates for filtering Contribution objects based on different criteria.
 * In general, predicates on entity fields are optional filters
 */
public class ContributionCriteria {
    public static Predicate<Contribution> isWinner() {
        return contribution -> contribution.getResult().equals(ContestResult.WINNER);
    }
    public static Predicate<Contribution> isLoser() {
        return contribution -> contribution.getResult().equals(ContestResult.LOSER);
    }
    public static Predicate<Contribution> hasId(UUID id) {
        if (id != null) {
            return contribution -> contribution.getId().equals(id);
        } else {
            return contribution -> true;
        }
    }
    @SafeVarargs
    public static Predicate<Contribution> criteriaBuilder(Predicate<Contribution>... predicates) {
        Predicate<Contribution> result = contribution -> true;
        for (Predicate<Contribution> predicate : predicates) {
            result = result.and(predicate);
        }
        return result;
    }
}
