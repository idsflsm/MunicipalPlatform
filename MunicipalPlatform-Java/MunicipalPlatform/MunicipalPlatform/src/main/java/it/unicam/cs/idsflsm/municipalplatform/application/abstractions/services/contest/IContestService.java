package it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.contest;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.contest.ContestDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.contest.ContributionDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedUserDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contest;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contribution;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
/**
 * Interface for Contest service class. It provides methods to manipulate persistent
 * contests and contributions in the database
 */
public interface IContestService {
    void saveInRepository(Contest contest);
    void saveInRepository(Contribution contribution);
    /**
     * Method that retrieves a list of Contest DTOs based on a given predicate
     *
     * @param predicate an Optional Predicate<Contest> that can be used to filter the contests
     * @return the list of Contest DTOs. If the predicate is present, the list will only contain
     * contests that satisfy the predicate. If no contests satisfy the predicate or if the
     * repository contains no contests, an empty list will be returned
     */
    List<ContestDto> getContests(Optional<Predicate<Contest>> predicate);
    /**
     * Method that retrieves a Contest DTO by its unique identifier
     *
     * @param id the UUID of the contest to be retrieved
     * @return the Contest DTO if a contest with the given UUID exists,
     * null otherwise
     */
    ContestDto getContestById(UUID id);
    /**
     * Method that adds a Contest entity to the platform
     *
     * @param contestDto a Contest DTO representing the entity to be added
     * @return the Contest DTO of the entity if it is added, null otherwise
     */
    ContestDto addContest(ContestDto contestDto);
    /**
     * Method that deletes a Contest entity by its unique identifier
     * and returns the deleted contest
     *
     * @param id the UUID of the contest to be deleted
     * @return the Contest DTO if a contest with the given UUID exists and was deleted,
     * null otherwise
     */
    ContestDto deleteContestById(UUID id);
    /**
     * Method that retrieves a list of Contribution DTOs based on a given predicate
     *
     * @param predicate an Optional Predicate<Contribution> that can be used to filter the contributions
     * @return the list of Contribution DTOs. If the predicate is present, the list will only contain
     * contributions that satisfy the predicate. If no contributions satisfy the predicate or if the
     * repository contains no contributions, an empty list will be returned
     */
    List<ContributionDto> getContributions(Optional<Predicate<Contribution>> predicate);
    /**
     * Method that retrieves a Contribution DTO by its unique identifier
     *
     * @param id the UUID of the contribution to be retrieved
     * @return the Contribution DTO if a contribution with the given UUID exists,
     * null otherwise
     */
    ContributionDto getContributionById(UUID id);
    /**
     * Method that adds a Contribution entity to the platform
     *
     * @param contributionDto a Contribution DTO representing the entity to be added
     * @return the Contribution DTO of the entity if it is added, null otherwise
     */
    ContributionDto addContribution(UUID idContest, UUID idParticipant, ContributionDto contributionDto);
    /**
     * Method that deletes a Contribution entity by its unique identifier
     * and returns the deleted contribution
     *
     * @param id the UUID of the contribution to be deleted
     * @return the Contribution DTO if a contribution with the given UUID exists and was deleted,
     * null otherwise
     */
    ContributionDto deleteContributionById(UUID id);
    /**
     * Method that validates a Contribution entity based on a provided
     * unique identifier and a validation flag
     *
     * @param idContribution the UUID of the contribution to be validated
     * @param validate       boolean flag indicating whether the contribution should be validated or not
     * @return the Contribution DTO if a contribution with the given UUID exists and was validated,
     * null otherwise
     */
    ContributionDto validateContribution(UUID idContribution, boolean validate);
    /**
     * Method that retrieves the winning contribution from a specific contest
     *
     * @param idContest the UUID of the contest
     * @return the Contribution DTO representing the winning contribution if it exists,
     * null otherwise
     */
    ContributionDto getWinnerContribution(UUID idContest);
    /**
     * Method that sets a Contribution entity as the winner of its associated contest
     *
     * @param idContribution the UUID of the contribution
     * @return the Contribution DTO representing the winning contribution
     * if it exists and meets the appropriate requirements,
     * null otherwise
     */
    ContributionDto setWinnerContribution(UUID idContribution);
    /**
     * Method that uploads a Contribution entity by its unique identifier
     * and returns the uploaded contribution
     *
     * @param idContribution the UUID of the contribution to be uploaded
     * @return the Contribution DTO if a contribution with the given UUID exists and is in UPLOADABLE state,
     * null otherwise
     */
    ContributionDto uploadContribution(UUID idContribution);
    /**
     * Method that deletes all contributions that have been marked as losers
     *
     * @param idContest an Optional UUID of the contest from which loser contributions are to be deleted.
     *                  If the UUID is not present, the method will apply to all contests
     * @return boolean flag indicating whether any contributions were deleted, false otherwise
     */
    boolean deleteAllLoserContributions(Optional<UUID> idContest);
    /**
     * Method that retrieves all participants, of a specific contest, that satisfy an optional predicate
     *
     * @param idContest the UUID of the contest from which participants are to be retrieved
     * @param predicate an Optional Predicate<AuthenticatedUser> for filtering.
     *                  If the predicate is not present, all participants will be returned
     * @return the list of AuthenticatedUser DTOs. If the predicate is present,
     * the list will only contain participants that satisfy the predicate.
     * If no participants satisfy the predicate
     * or if the contest does not exist, an empty list will be returned
     */
    List<AuthenticatedUserDto> getParticipants(UUID idContest, Optional<Predicate<AuthenticatedUser>> predicate);
    /**
     * Method that retrieves a participant, from a specific contest, by its unique identifier
     *
     * @param idContest           the UUID of the specific contest
     * @param idAuthenticatedUser the UUID of the participant to be retrieved
     * @return the AuthenticatedUser DTO if a participant with the given UUID exists, and
     * is a participant of the specific contest,
     * null otherwise
     */
    AuthenticatedUserDto getParticipantById(UUID idContest, UUID idAuthenticatedUser);
    /**
     * Method that adds an AuthenticatedUser entity as a participant for a specific contest
     *
     * @param idContest the UUID of the contest to which the participant is to be added
     * @param userDto   an AuthenticatedUser DTO representing the participant to be added
     * @return the AuthenticatedUser DTO if the participant was successfully added,
     * null otherwise
     */
    AuthenticatedUserDto addParticipant(UUID idContest, AuthenticatedUserDto userDto);
    /**
     * Method that removes an AuthenticatedUser entity as a participant for a specific contest
     *
     * @param idContest the UUID of the contest to which the participant is to be removed
     * @param userDto   an AuthenticatedUser DTO representing the participant to be removed
     * @return the AuthenticatedUser DTO if the participant was successfully removed,
     * null otherwise
     */
    AuthenticatedUserDto deleteParticipant(UUID idContest, AuthenticatedUserDto userDto);
}
