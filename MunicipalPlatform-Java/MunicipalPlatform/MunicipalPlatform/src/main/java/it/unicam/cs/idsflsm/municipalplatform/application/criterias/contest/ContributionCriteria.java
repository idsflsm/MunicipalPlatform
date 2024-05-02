package it.unicam.cs.idsflsm.municipalplatform.application.criterias.contest;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contribution;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContestResult;

import java.util.function.Predicate;
public class ContributionCriteria {
    public static Predicate<Contribution> isWinner() {
        return contribution -> contribution.getResult().equals(ContestResult.WINNER);
    }
    public static Predicate<Contribution> isLoser() {
        return contribution -> contribution.getResult().equals(ContestResult.LOSER);
    }
}
