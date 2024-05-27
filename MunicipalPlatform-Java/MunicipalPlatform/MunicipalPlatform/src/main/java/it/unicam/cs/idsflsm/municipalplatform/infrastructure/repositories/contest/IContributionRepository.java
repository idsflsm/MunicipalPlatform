package it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.contest;
import it.unicam.cs.idsflsm.municipalplatform.application.criterias.contest.ContestCriteria;
import it.unicam.cs.idsflsm.municipalplatform.application.criterias.contest.ContributionCriteria;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contest;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contribution;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.GenericRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
@Repository
public interface IContributionRepository extends GenericRepository<Contribution, UUID> {

}
