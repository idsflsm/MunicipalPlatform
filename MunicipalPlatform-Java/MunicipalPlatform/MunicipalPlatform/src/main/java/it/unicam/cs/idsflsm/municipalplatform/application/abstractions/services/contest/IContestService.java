package it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.contest;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.contest.ContestDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.contest.ContributionDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedTouristDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedUserDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contest;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contribution;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedTourist;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
public interface IContestService {
    void saveInRepository(Contest contest);
    void saveInRepository(Contribution contribution);
//    void deleteFromRepository(Contribution contribution);
    List<ContestDto> getAllContests(Optional<Predicate<Contest>> predicate);
    ContestDto getContestById(UUID id);
    ContestDto addContest(ContestDto contestDto);
    boolean deleteContestById(UUID id);
    boolean deleteContest(ContestDto contestDto, Optional<Predicate<Contest>> predicate);

    List<ContributionDto> getAllContributions(Optional<Predicate<Contribution>> predicate);
    ContributionDto getContributionById(UUID id);
    ContributionDto addContribution(UUID idContest, UUID idParticipant, ContributionDto contributionDto);
    boolean deleteContributionById(UUID id);
//    boolean deleteContribution(ContributionDto contributionDto, Optional<Predicate<Contribution>> predicate);
    ContributionDto validateContribution(UUID idContribution, boolean validate);
    ContributionDto getWinnerContribution(UUID idContest);
    ContributionDto setWinnerContribution(UUID idContribution);
    ContributionDto uploadContribution(UUID idContribution);
    boolean deleteAllLoserContributions(Optional<UUID> idContest);

    List<AuthenticatedUserDto> getAllParticipants(UUID idContest, Optional<Predicate<AuthenticatedUser>> predicate);
    AuthenticatedUserDto getParticipantById(UUID idContest, UUID idAuthenticatedUser);
    AuthenticatedUserDto addParticipant(UUID idContest, AuthenticatedUserDto userDto);
    AuthenticatedUserDto deleteParticipant(UUID idContest, AuthenticatedUserDto userDto);
}
