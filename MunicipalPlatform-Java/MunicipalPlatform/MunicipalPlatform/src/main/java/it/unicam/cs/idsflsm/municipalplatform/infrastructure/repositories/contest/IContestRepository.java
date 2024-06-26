package it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.contest;
import it.unicam.cs.idsflsm.municipalplatform.application.criterias.contest.ContestCriteria;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contest;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.GenericRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
/**
 * Interface that represents the repository for Contest entity
 */
@Repository
public interface IContestRepository extends GenericRepository<Contest, UUID> {
    default List<Contest> findByExpiryDate(LocalDate expiryDate) {
        return findByPredicate(ContestCriteria.hasExpiryDate(Date.toDate(expiryDate)));
    }
}
