package it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.contest;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
public interface IContestRepository extends JpaRepository<Contest, UUID> {
}
