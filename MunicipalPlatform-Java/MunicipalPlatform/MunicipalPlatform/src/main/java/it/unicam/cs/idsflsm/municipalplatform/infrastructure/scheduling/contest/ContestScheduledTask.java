package it.unicam.cs.idsflsm.municipalplatform.infrastructure.scheduling.contest;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contest;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.contest.IContestRepository;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.scheduling.GenericScheduledTask;
import org.springframework.stereotype.Component;
@Component
public class ContestScheduledTask extends GenericScheduledTask<Contest> {
    public ContestScheduledTask(IContestRepository contestRepository) {
        super(contestRepository);
    }
    public void deleteContestsByExpiryDate() {
        this.setMethodName("findByExpiryDate");
        this.deleteEntities();
    }
}
