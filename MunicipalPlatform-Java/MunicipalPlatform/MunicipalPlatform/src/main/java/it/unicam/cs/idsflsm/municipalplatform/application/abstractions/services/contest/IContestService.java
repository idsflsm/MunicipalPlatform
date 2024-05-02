package it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.contest;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.contest.ContestDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.contest.ContributionDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedTouristDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedUserDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contest;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contribution;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
public interface IContestService {
    List<ContestDto> getAllContests(Optional<Predicate<Contest>> predicate);
    ContestDto getContestById(UUID id);
    List<ContributionDto> getAllContributions(Optional<Predicate<Contribution>> predicate);
    ContributionDto getContributionById(UUID id);
    boolean addContest(ContestDto contestDto);
    boolean addContribution(ContributionDto contributionDto);
    boolean addParticipant(UUID idContest, AuthenticatedTouristDto touristDto);
    boolean deleteContestById(UUID id);
    boolean deleteContest(ContestDto contestDto, Optional<Predicate<Contest>> predicate);
    boolean deleteContributionById(UUID id);
    boolean deleteContribution(ContributionDto contributionDto, Optional<Predicate<Contribution>> predicate);

    ContributionDto getWinnerContribution(UUID idContest);
    boolean uploadContribution(ContributionDto contributionDto);
    boolean deleteAllLoserContributions(Optional<UUID> idContest);
}
