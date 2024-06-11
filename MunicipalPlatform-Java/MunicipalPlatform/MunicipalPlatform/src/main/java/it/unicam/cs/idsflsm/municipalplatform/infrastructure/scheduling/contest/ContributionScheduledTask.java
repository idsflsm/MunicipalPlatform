package it.unicam.cs.idsflsm.municipalplatform.infrastructure.scheduling.contest;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contribution;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.contest.IContributionRepository;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.scheduling.GenericScheduledTask;
import org.springframework.stereotype.Component;
/**
 * Scheduled task class for managing deletion of Contribution entity
 */
@Component
public class ContributionScheduledTask extends GenericScheduledTask<Contribution> {
    public ContributionScheduledTask(IContributionRepository contributionRepository) {
        super(contributionRepository);
    }
    /**
     * Method that deletes attachments based on their LOSER result
     */
    public void deleteContestsByIsLoser() {
        this.setMethodName("findByIsLoser");
        this.deleteEntities();
    }
}