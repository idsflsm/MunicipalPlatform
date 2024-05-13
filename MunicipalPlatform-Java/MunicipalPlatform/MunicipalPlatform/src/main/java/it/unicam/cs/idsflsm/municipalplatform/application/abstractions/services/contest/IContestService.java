package it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.contest;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.contest.ContestDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.contest.ContributionDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedTouristDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedUserDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contest;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contribution;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedTourist;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
public interface IContestService {
    void saveInRepository(Contest contest);
    void saveInRepository(Contribution contribution);
    List<ContestDto> getAllContests(Optional<Predicate<Contest>> predicate);
    ContestDto getContestById(UUID id);
    boolean addContest(ContestDto contestDto);
    boolean deleteContestById(UUID id);
//    boolean deleteContest(ContestDto contestDto, Optional<Predicate<Contest>> predicate);

    List<ContributionDto> getAllContributions(Optional<Predicate<Contribution>> predicate);
    ContributionDto getContributionById(UUID id);
    boolean addContribution(UUID idContest, ContributionDto contributionDto);
    boolean deleteContributionById(UUID id);
//    boolean deleteContribution(ContributionDto contributionDto, Optional<Predicate<Contribution>> predicate);
    boolean validateContribution(ContributionDto contributionDto, Optional<Predicate<Contribution>> predicate, boolean validate);
    ContributionDto getWinnerContribution(UUID idContest);
    Optional<Contribution> uploadContribution(ContributionDto contributionDto);
    boolean deleteAllLoserContributions(Optional<UUID> idContest);

    List<AuthenticatedTouristDto> getAllParticipants(UUID idContest, Optional<Predicate<AuthenticatedTourist>> predicate);
    AuthenticatedTouristDto getParticipantById(UUID idContest, UUID idAuthenticatedTourist);
    boolean addParticipant(UUID idContest, AuthenticatedTouristDto touristDto);
    boolean deleteParticipant(UUID idContest, AuthenticatedTouristDto touristDto);
}
