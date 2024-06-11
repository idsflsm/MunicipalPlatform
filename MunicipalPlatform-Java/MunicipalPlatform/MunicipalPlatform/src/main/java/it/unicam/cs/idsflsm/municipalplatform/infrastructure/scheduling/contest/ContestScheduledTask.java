package it.unicam.cs.idsflsm.municipalplatform.infrastructure.scheduling.contest;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contest;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.contest.IContestRepository;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.scheduling.GenericScheduledTask;
import org.springframework.stereotype.Component;
/**
 * Scheduled task class for managing deletion of Contest entity
 */
@Component
public class ContestScheduledTask extends GenericScheduledTask<Contest> {
    public ContestScheduledTask(IContestRepository contestRepository) {
        super(contestRepository);
    }
    /**
     * Method that deletes contests based on their expiry date
     */
    public void deleteContestsByExpiryDate() {
        this.setMethodName("findByExpiryDate");
        this.deleteEntities();
    }
}
