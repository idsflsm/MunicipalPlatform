package it.unicam.cs.idsflsm.municipalplatform.api.controllers.contest;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidDateFormatException;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidLatitudeException;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidLongitudeException;
import it.unicam.cs.idsflsm.municipalplatform.api.helpers.content.itinerary.ItineraryHelper;
import it.unicam.cs.idsflsm.municipalplatform.api.helpers.content.poi.POIHelper;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.contest.IContestService;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.user.IUserService;
import it.unicam.cs.idsflsm.municipalplatform.application.criterias.contest.ContestCriteria;
import it.unicam.cs.idsflsm.municipalplatform.application.criterias.user.authenticated.AuthenticatedUserCriteria;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.PendingItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.PendingPOIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.contest.ContestDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.contest.ContributionDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedUserDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.contest.*;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contest;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contribution;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserPermission;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
@RestController
@Validated
@RequestMapping("/api/contests")
@AllArgsConstructor(onConstructor_ = @Autowired)
public class ContestController {
    /**
     * The service for Contest and Contribution entities
     */
    private final IContestService _contestService;
    /**
     * The service for User entities
     */
    private final IUserService _userService;
    /**
     * The helper for Itinerary entity
     */
    private final ItineraryHelper _itineraryHelper;
    /**
     * The helper for POI entity
     */
    private final POIHelper _poiHelper;
    /**
     * Method that retrieves a list of Contest DTOs after filtering
     *
     * @param name         the name of desired contests
     * @param author       the author of desired contests
     * @param description  the description of desired contests
     * @param creationDate the creation date of desired contests
     * @param expiryDate   the expiry date of desired contests
     * @param hasWinner    the flag for desired contests that have a winner
     * @return the list of found Contest DTOs, based on params
     */
    @GetMapping
    public ResponseEntity<?> getContests
    (@RequestParam(required = false) String name,
     @RequestParam(required = false) String author,
     @RequestParam(required = false) String description,
     @RequestParam(required = false) String creationDate,
     @RequestParam(required = false) String expiryDate,
     @RequestParam(required = false) boolean hasWinner) {
        try {
            Predicate<Contest> criterias = getContestsCriterias(name, author, description, creationDate, expiryDate, hasWinner);
            List<ContestDto> contestDtos = _contestService.getContests(Optional.of(criterias));
            if (!contestDtos.isEmpty()) {
                return new ResponseEntity<>(contestDtos, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (InvalidDateFormatException e1) {
            return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    private Predicate<Contest> getContestsCriterias(String name, String author, String description, String creationDate, String expiryDate, boolean hasWinner) {
        Date cd = (creationDate != null) ? Date.fromString(creationDate) : null;
        Date ed = (expiryDate != null) ? Date.fromString(expiryDate) : null;
        return ContestCriteria.criteriaBuilder(
                ContestCriteria.hasName(name),
                ContestCriteria.hasAuthor(author),
                ContestCriteria.hasDescription(description),
                ContestCriteria.hasCreationDate(cd),
                ContestCriteria.hasExpiryDate(ed),
                ContestCriteria.hasWinner(hasWinner)
        );
    }
    /**
     * Method that retrieves a Contest DTO by its unique identifier
     *
     * @param id the UUID of desired contest
     * @return the Contest DTO if exists
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getContestById(@PathVariable("id") UUID id) {
        ContestDto contestDto = _contestService.getContestById(id);
        if (contestDto != null) {
            return new ResponseEntity<>(contestDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    /**
     * Method that adds a Contest entity to the platform
     *
     * @param request the request for adding a new contest
     * @return the Contest DTO if has been added
     */
    @PostMapping
    public ResponseEntity<?> addContest(@RequestBody AddContestRequest request) {
        try {
            if (_userService.appropriateUser(request.getIdUser(), UserPermission.ANIMATOR_CONTEST_CREATE)) {
                ContestDto contestDto = new ContestDto();
                ContestDto result = _contestService.addContest(modifyContestConfiguration(contestDto, request));
                if (result != null) {
                    return new ResponseEntity<>(result, HttpStatus.CREATED);
                }
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (InvalidDateFormatException e1) {
            return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
//    TODO : FOR TESTING PURPOSES ONLY
    /**
     * Method that deletes a Contest entity by its unique identifier
     * and returns the deleted Contest
     *
     * @param id the UUID of desired contest
     * @return the Contest DTO if has been deleted
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContest(@PathVariable("id") UUID id) {
        ContestDto result = _contestService.deleteContestById(id);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    /**
     * Method that retrieves a list of Contribution DTOs
     *
     * @param idContest the UUID of desired contest
     * @param idUser    the UUID of the user performing the operation
     * @return the list of found Contribution DTOs
     */
    @GetMapping("/{idContest}/contributions")
    public ResponseEntity<?> getContributions(@PathVariable(value = "idContest", required = false) UUID idContest, @RequestParam UUID idUser) {
        if (_userService.appropriateUser(idUser, UserPermission.ANIMATOR_CONTRIBUTION_READ)) {
            Optional<Predicate<Contribution>> predicate = (idContest != null)
                    ? (Optional.of(contribution -> contribution.getContest().getId().equals(idContest)))
                    : Optional.empty();
            List<ContributionDto> contributionDtos = _contestService.getContributions(predicate);
            if (!contributionDtos.isEmpty()) {
                return new ResponseEntity<>(contributionDtos, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    /**
     * Method that retrieves a Contribution DTO by its unique identifier
     *
     * @param id     the UUID of desired contribution
     * @param idUser the UUID of the user performing the operation
     * @return the Contribution DTO if exists
     */
    @GetMapping("/contributions/{id}")
    public ResponseEntity<?> getContributionById(@PathVariable("id") UUID id, @RequestParam UUID idUser) {
        if (_userService.appropriateUser(idUser, UserPermission.ANIMATOR_CONTRIBUTION_READ)) {
            ContributionDto contributionDto = _contestService.getContributionById(id);
            if (contributionDto != null) {
                return new ResponseEntity<>(contributionDto, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    /**
     * Method that adds a POI entity as a Contribution entity on the platform
     *
     * @param idContest the UUID of desired contest
     * @param request   the request for adding a new POI as a contribution
     * @return the Contribution DTO if has been added
     */
    @PostMapping("/{idContest}/contributions/poi")
    public ResponseEntity<?> addPOIAsContribution(@PathVariable("idContest") UUID idContest, @RequestBody AddPOIAsContributionRequest request) {
        try {
            if (_userService.appropriateUser(request.getIdUser(), UserPermission.AUTHTOURIST_CONTRIBUTION_CREATE)) {
                PendingPOIDto poiDto = new PendingPOIDto();
                ContributionDto contributionDto = new ContributionDto();
                contributionDto.setPoi(_poiHelper.modifyPoiConfiguration(poiDto, request, ContentState.VALIDABLE));
                ContributionDto result = _contestService.addContribution(idContest, request.getIdUser(), contributionDto);
                if (result != null) {
                    return new ResponseEntity<>(result, HttpStatus.CREATED);
                }
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (InvalidLatitudeException | InvalidLongitudeException | InvalidDateFormatException e1) {
            return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    /**
     * Method that adds an Itinerary entity as a Contribution entity on the platform
     *
     * @param idContest the UUID of desired contest
     * @param request   the request for adding a new Itinerary as a contribution
     * @return the Contribution DTO if has been added
     */
    @PostMapping("/{idContest}/contributions/itinerary")
    public ResponseEntity<?> addItineraryAsContribution(@PathVariable("idContest") UUID idContest, @RequestBody AddItineraryAsContributionRequest request) {
        try {
            if (_userService.appropriateUser(request.getIdUser(), UserPermission.AUTHTOURIST_CONTRIBUTION_CREATE)) {
                PendingItineraryDto itineraryDto = new PendingItineraryDto();
                ContributionDto contributionDto = new ContributionDto();
                contributionDto.setItinerary(_itineraryHelper.modifyItineraryConfiguration(itineraryDto, request, ContentState.VALIDABLE));
                ContributionDto result = _contestService.addContribution(idContest, request.getIdUser(), contributionDto);
                if (result != null) {
                    return new ResponseEntity<>(result, HttpStatus.CREATED);
                }
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (InvalidLatitudeException | InvalidLongitudeException | InvalidDateFormatException e1) {
            return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
//    TODO : FOR TESTING PURPOSES ONLY
    /**
     * Method that deletes a Contribution entity by its unique identifier
     * and returns the deleted Contribution
     *
     * @param id the UUID of desired contribution
     * @return the Contribution DTO if has been deleted
     */
    @DeleteMapping("/contributions/{id}")
    public ResponseEntity<?> deleteContribution(@PathVariable("id") UUID id) {
        ContributionDto result = _contestService.deleteContributionById(id);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    /**
     * Method that validates a Contribution entity based on a provided
     * unique identifier and a validation flag
     *
     * @param id      the UUID of desired contribution
     * @param request the request for validating the desired contribution
     * @return the Contribution DTO if has been validated
     */
    @PatchMapping("/contributions/{id}/validate")
    public ResponseEntity<?> validateContribution(@PathVariable("id") UUID id, @RequestBody ValidateContributionRequest request) {
        if (_userService.appropriateUser(request.getIdUser(), UserPermission.ANIMATOR_CONTEST_VALIDATE)) {
            ContributionDto result = _contestService.validateContribution(id, request.isValidate());
            if (result != null) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    /**
     * Method that uploads a Contribution entity by its unique identifier
     *
     * @param id     the UUID of desired contribution
     * @param idUser the UUID of the user performing the operation
     * @return the Contribution DTO if has been uploaded
     */
    @PatchMapping("/contributions/{id}/upload")
    public ResponseEntity<?> uploadContribution(@PathVariable("id") UUID id, @RequestParam UUID idUser) {
        if (_userService.appropriateUser(idUser, UserPermission.ANIMATOR_CONTRIBUTION_UPLOAD)) {
            ContributionDto result = _contestService.uploadContribution(id);
            if (result != null) {
                if (result.getPoi() != null && result.getItinerary() == null) {
                    return new ResponseEntity<>(result.getPoi(), HttpStatus.OK);
                }
                if (result.getPoi() == null && result.getItinerary() != null) {
                    return new ResponseEntity<>(result.getItinerary(), HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    /**
     * Method that retrieves the winning contribution from a specific contest
     *
     * @param idContest the UUID of desired contest
     * @param idUser    the UUID of the user performing the operation
     * @return the Contribution DTO if exists
     */
    @GetMapping("/{idContest}/contributions/win")
    public ResponseEntity<?> getWinnerContribution(@PathVariable("idContest") UUID idContest, @RequestParam UUID idUser) {
        if (_userService.appropriateUser(idUser, UserPermission.ANIMATOR_CONTRIBUTION_READ)) {
            ContributionDto contributionDto = _contestService.getWinnerContribution(idContest);
            if (contributionDto != null) {
                return new ResponseEntity<>(contributionDto, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    /**
     * Method that sets a Contribution entity as the winner of its associated contest
     *
     * @param id     the UUID of desired contribution
     * @param idUser the UUID of the user performing the operation
     * @return the Contribution DTO if has been set as winner
     */
    @PatchMapping("/contributions/{id}/win")
    public ResponseEntity<?> setWinnerContribution(@PathVariable("id") UUID id, @RequestParam UUID idUser) {
        if (_userService.appropriateUser(idUser, UserPermission.ANIMATOR_CONTRIBUTION_WINNER_SET)) {
            ContributionDto result = _contestService.setWinnerContribution(id);
            if (result != null) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    /**
     * Method that deletes all contributions that have been marked as losers
     *
     * @param idContest the UUID of desired contest (optional)
     * @param idUser    the UUID of the user performing the operation
     * @return the result of the deletion
     */
    @DeleteMapping("/{idContest}/contributions/lose")
    public ResponseEntity<?> deleteLoserContributions(@PathVariable(value = "idContest", required = false) UUID idContest, @RequestParam UUID idUser) {
        if (_userService.appropriateUser(idUser, UserPermission.ANIMATOR_CONTRIBUTION_LOSER_DELETE)) {
            Optional<UUID> id = (idContest != null) ? Optional.of(idContest) : Optional.empty();
            boolean result = _contestService.deleteAllLoserContributions(id);
            if (result) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    /**
     * Method that retrieves all participants, of a specific contest, after filtering
     *
     * @param idContest the UUID of desired contest
     * @param idUser    the UUID of the user performing the operation
     * @param username  the username of desired participants
     * @param name      the name of desired participants
     * @param surname   the surname of desired participants
     * @return the list of found AuthenticatedUser DTOs, based on params
     */
    @GetMapping("/{idContest}/participants")
    public ResponseEntity<?> getParticipants
    (@PathVariable("idContest") UUID idContest,
     @RequestParam(required = true) UUID idUser,
     @RequestParam(required = false) String username,
     @RequestParam(required = false) String name,
     @RequestParam(required = false) String surname) {
        if (_userService.appropriateUser(idUser, UserPermission.ANIMATOR_CONTEST_PARTICIPANT_READ)) {
            Predicate<AuthenticatedUser> criterias = getContestsCriterias(username, name, surname);
            List<AuthenticatedUserDto> authenticatedUserDtos = _contestService.getParticipants(idContest, Optional.of(criterias));
            if (!authenticatedUserDtos.isEmpty()) {
                return new ResponseEntity<>(authenticatedUserDtos, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    private Predicate<AuthenticatedUser> getContestsCriterias(String username, String name, String surname) {
        return AuthenticatedUserCriteria.criteriaBuilder(
                AuthenticatedUserCriteria.hasUsername(username),
                AuthenticatedUserCriteria.hasName(name),
                AuthenticatedUserCriteria.hasSurname(surname)
        );
    }
    /**
     * Method that retrieves a participant, from a specific contest, by its unique identifier
     *
     * @param idContest     the UUID of desired contest
     * @param idParticipant the UUID of desired participant
     * @param idUser        the UUID of the user performing the operation
     * @return the AuthenticatedUser DTO if exists
     */
    @GetMapping("/{idContest}/participants/{idUser}")
    public ResponseEntity<?> getParticipantById(@PathVariable("idContest") UUID idContest, @PathVariable("idUser") UUID idParticipant, @RequestParam UUID idUser) {
        if (_userService.appropriateUser(idUser, UserPermission.ANIMATOR_CONTEST_PARTICIPANT_READ)) {
            AuthenticatedUserDto userDto = _contestService.getParticipantById(idContest, idParticipant);
            if (userDto != null) {
                return new ResponseEntity<>(userDto, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    /**
     * Method that adds an AuthenticatedUser entity as a participant for a specific contest
     *
     * @param idContest the UUID of desired contest
     * @param request   the request for adding a user as participant
     * @return the AuthenticatedUser DTO if has been added as participant
     */
    @PostMapping("/{idContest}/participants")
    public ResponseEntity<?> addParticipant(@PathVariable("idContest") UUID idContest, @RequestBody AddParticipantRequest request) {
        AuthenticatedUserDto userDto = _userService.findByUsername(request.getUsername());
        if (userDto != null && _userService.appropriateUser(userDto.getId(), UserPermission.AUTHTOURIST_CONTEST_PARTICIPATE)) {
            AuthenticatedUserDto result = _contestService.addParticipant(idContest, userDto);
            if (result != null) {
                return new ResponseEntity<>(result, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    /**
     * Method that removes an AuthenticatedUser entity as a participant for a specific contest
     *
     * @param idContest the UUID of desired contest
     * @param request   the request for deleting a participant
     * @return the AuthenticatedUser DTO if has been deleted as participant
     */
    @DeleteMapping("/{idContest}/participants")
    public ResponseEntity<?> deleteParticipant(@PathVariable("idContest") UUID idContest, @RequestBody DeleteParticipantRequest request) {
        AuthenticatedUserDto userDto = _userService.findByUsername(request.getUsername());
        if (userDto != null && _userService.appropriateUser(request.getIdUser(), UserPermission.ANIMATOR_CONTEST_PARTICIPANT_DELETE)) {
            AuthenticatedUserDto result = _contestService.deleteParticipant(idContest, userDto);
            if (result != null) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    private <T extends ModifyContestRequest> ContestDto modifyContestConfiguration(ContestDto contestDto, T request) {
        contestDto.setName(request.getName());
        contestDto.setAuthor(request.getAuthor());
        contestDto.setDescription(request.getDescription());
        LocalDate localDate = LocalDate.now();
        contestDto.setCreationDate(Date.toDate(localDate));
        contestDto.setExpiryDate(Date.fromString(request.getExpiryDate()));
        return contestDto;
    }
}