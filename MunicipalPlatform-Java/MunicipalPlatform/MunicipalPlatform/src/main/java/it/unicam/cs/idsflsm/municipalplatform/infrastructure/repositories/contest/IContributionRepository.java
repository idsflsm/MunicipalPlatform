package it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.contest;
import it.unicam.cs.idsflsm.municipalplatform.application.criterias.contest.ContributionCriteria;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contribution;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.GenericRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
/**
 * Interface that represents the repository for Contribution entity
 */
@Repository
public interface IContributionRepository extends GenericRepository<Contribution, UUID> {
    default List<Contribution> findByIsLoser() {
        return findByPredicate(ContributionCriteria.isLoser().and(
                contribution -> (Date.toDate(LocalDate.now())).beforeThan
                        (contribution.getContent().getExpiryDate())
        ));
    }
}
