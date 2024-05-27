package it.unicam.cs.idsflsm.municipalplatform.api.controllers.contest;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidDateFormatException;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidLatitudeException;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidLongitudeException;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.content.itinerary.IItineraryService;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.content.poi.IPOIService;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.contest.IContestService;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.user.IUserService;
import it.unicam.cs.idsflsm.municipalplatform.application.criterias.contest.ContestCriteria;
import it.unicam.cs.idsflsm.municipalplatform.application.criterias.user.AuthenticatedUserCriteria;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.itinerary.GenericItineraryMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.poi.GenericPOIMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.contest.ContestDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.contest.ContributionDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.ItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.PendingItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.POIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.PendingPOIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedUserDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.contest.*;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contest;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contribution;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Coordinates;
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
    private IContestService _contestService;
    private IPOIService _poiService;
    private IItineraryService _itineraryService;
    private IUserService _userService;
    @GetMapping
    public ResponseEntity<?> getAllContests
            (@RequestParam(required = false) String name,
             @RequestParam(required = false) String author,
             @RequestParam(required = false) String description,
             @RequestParam(required = false) String creationDate,
             @RequestParam(required = false) String expiryDate,
             @RequestParam(required = false) boolean hasWinner) {
        try {
            Predicate<Contest> criterias = getAllCriterias(name, author, description, creationDate, expiryDate, hasWinner);
            List<ContestDto> contestDtos = _contestService.getAllContests(Optional.of(criterias));
            if (!contestDtos.isEmpty()) {
                return new ResponseEntity<>(contestDtos, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (InvalidDateFormatException e1) {
            return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    private Predicate<Contest> getAllCriterias(String name, String author, String description, String creationDate, String expiryDate, boolean hasWinner) {
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
    @GetMapping("/{id}")
    public ResponseEntity<?> getContestById(@PathVariable("id") UUID id) {
        ContestDto contestDto = _contestService.getContestById(id);
        if (contestDto != null) {
            return new ResponseEntity<>(contestDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
    @PostMapping
    public ResponseEntity<?> addContest(@RequestBody AddContestRequest request) {
        if (_userService.appropriateUser(request.getIdUser(), UserPermission.ANIMATOR_CONTEST_CREATE)) {
            try {
                ContestDto contestDto = new ContestDto();
                ContestDto result = _contestService.addContest(modifyContestConfiguration(contestDto, request));
                if (result != null) {
                    return new ResponseEntity<>(result, HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            } catch (InvalidDateFormatException e1) {
                return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteContest(@PathVariable("id") UUID id) {
//        boolean result = _contestService.deleteContestById(id);
//        if (result) {
//            return new ResponseEntity<>(HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
    @GetMapping("/contributions/contests/{idContest}")
    public ResponseEntity<?> getAllContributions(@PathVariable(value = "idContest", required = false) UUID idContest, @RequestParam UUID idUser) {
        if (_userService.appropriateUser(idUser, UserPermission.ANIMATOR_CONTRIBUTION_READ)) {
            Optional<Predicate<Contribution>> predicate = (idContest != null)
                    ? (Optional.of(contribution -> contribution.getContest().getId().equals(idContest)))
                    : Optional.empty();
            List<ContributionDto> contributionDtos = _contestService.getAllContributions(predicate);
            if (!contributionDtos.isEmpty()) {
                return new ResponseEntity<>(contributionDtos, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @GetMapping("/contributions/{idContribution}")
    public ResponseEntity<?> getContribution(@PathVariable("idContribution") UUID idContribution, @RequestParam UUID idUser) {
        if (_userService.appropriateUser(idUser, UserPermission.ANIMATOR_CONTRIBUTION_READ)) {
            ContributionDto contributionDto = _contestService.getContributionById(idContribution);
            if (contributionDto != null) {
                return new ResponseEntity<>(contributionDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @PostMapping("/contributions/poi/contests/{idContest}")
    public ResponseEntity<?> addPOIAsContribution(@PathVariable("idContest") UUID idContest, @RequestBody AddPOIAsContributionRequest request) {
        if (_userService.appropriateUser(request.getIdUser(), UserPermission.AUTHTOURIST_CONTRIBUTION_CREATE)) {
            try {
                PendingPOIDto poiDto = new PendingPOIDto();
                ContributionDto contributionDto = new ContributionDto();
                contributionDto.setPoi(modifyPoiAsContributionConfiguration(poiDto, request));
                ContributionDto result = _contestService.addContribution(idContest, request.getIdUser(), contributionDto);
                if (result != null) {
                    return new ResponseEntity<>(result, HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            } catch (InvalidLatitudeException | InvalidLongitudeException | InvalidDateFormatException e1) {
                return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @PostMapping("/contributions/itinerary/contests/{idContest}")
    public ResponseEntity<?> addItineraryAsContribution(@PathVariable("idContest") UUID idContest, @RequestBody AddItineraryAsContributionRequest request) {
        if (_userService.appropriateUser(request.getIdUser(), UserPermission.AUTHTOURIST_CONTRIBUTION_CREATE)) {
            try {
                PendingItineraryDto itineraryDto = new PendingItineraryDto();
                ContributionDto contributionDto = new ContributionDto();
                contributionDto.setItinerary(modifyItineraryAsContributionConfiguration(itineraryDto, request));
                ContributionDto result = _contestService.addContribution(idContest, request.getIdUser(), contributionDto);
                if (result != null) {
                    return new ResponseEntity<>(result, HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            } catch (InvalidLatitudeException | InvalidLongitudeException | InvalidDateFormatException e1) {
                return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
//    @DeleteMapping("/contributions/{id}")
//    public ResponseEntity<?> deleteContribution(@PathVariable("id") UUID id) {
//        boolean result = _contestService.deleteContributionById(id);
//        if (result) {
//            return new ResponseEntity<>(HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
    @PutMapping("/contributions/validable/{id}")
    public ResponseEntity<?> validateContribution(@PathVariable("id") UUID id, @RequestBody ValidateContributionRequest request) {
        if (_userService.appropriateUser(request.getIdUser(), UserPermission.ANIMATOR_CONTEST_VALIDATE)) {
            ContributionDto result = _contestService.validateContribution(id, request.isValidate());
            if (result != null) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @PatchMapping("/contributions/uploadable/{idContribution}")
    public ResponseEntity<?> uploadContribution(@PathVariable("idContribution") UUID idContribution, @RequestParam UUID idUser) {
        if (_userService.appropriateUser(idUser, UserPermission.ANIMATOR_CONTRIBUTION_UPLOAD)) {
            ContributionDto result = _contestService.uploadContribution(idContribution);
//            ContributionDto result = _contestService.getContributionById(idContribution);
            if (result != null) {
                if (result.getPoi() != null && result.getItinerary() == null) {
                    result.getPoi().setState(ContentState.UPLOADED);
                    _poiService.saveInRepository(GenericPOIMapper.toEntity(result.getPoi(), true));
//                    _contestService.deleteFromRepository(ContributionMapper.toEntity(result, true));
                    return new ResponseEntity<>(result.getPoi(), HttpStatus.OK);
                }
                if (result.getPoi() == null && result.getItinerary() != null) {
                    result.getItinerary().setState(ContentState.UPLOADED);
                    _itineraryService.saveInRepository(GenericItineraryMapper.toEntity(result.getItinerary(), true));
//                    _contestService.deleteFromRepository(ContributionMapper.toEntity(result, true));
                    return new ResponseEntity<>(result.getItinerary(), HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @GetMapping("/contributions/winners/{idContest}")
    public ResponseEntity<?> getWinnerContribution(@PathVariable("idContest") UUID idContest, @RequestParam UUID idUser) {
        if (_userService.appropriateUser(idUser, UserPermission.ANIMATOR_CONTRIBUTION_READ)) {
            ContributionDto contributionDto = _contestService.getWinnerContribution(idContest);
            if (contributionDto != null) {
                return new ResponseEntity<>(contributionDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @PatchMapping("/contributions/winners/{idContribution}")
    public ResponseEntity<?> setWinnerContribution(@PathVariable("idContribution") UUID idContribution, @RequestParam UUID idUser) {
        if (_userService.appropriateUser(idUser, UserPermission.ANIMATOR_CONTRIBUTION_WINNER_SET)) {
            ContributionDto result = _contestService.setWinnerContribution(idContribution);
            if (result != null) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @DeleteMapping("/contributions/losers/{idContest}")
    public ResponseEntity<?> deleteAllLoserContributions(@PathVariable(value = "idContest", required = false) UUID idContest, @RequestParam UUID idUser) {
        if (_userService.appropriateUser(idUser, UserPermission.ANIMATOR_CONTRIBUTION_LOSER_DELETE)) {
            Optional<UUID> id = (idContest != null) ? Optional.of(idContest) : Optional.empty();
            boolean result = _contestService.deleteAllLoserContributions(id);
            if (result) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @GetMapping("/participants/contests/{idContest}")
    public ResponseEntity<?> getAllParticipants
            (@PathVariable("idContest") UUID idContest,
             @RequestParam(required = true) UUID idUser,
             @RequestParam(required = false) String username,
             @RequestParam(required = false) String name,
             @RequestParam(required = false) String surname) {
        if (_userService.appropriateUser(idUser, UserPermission.ANIMATOR_CONTEST_PARTICIPANT_READ)) {
            Predicate<AuthenticatedUser> criterias = getAllCriterias(username, name, surname);
            List<AuthenticatedUserDto> authenticatedUserDtos = _contestService.getAllParticipants(idContest, Optional.of(criterias));
            if (!authenticatedUserDtos.isEmpty()) {
                return new ResponseEntity<>(authenticatedUserDtos, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    private Predicate<AuthenticatedUser> getAllCriterias(String username, String name, String surname) {
        return AuthenticatedUserCriteria.criteriaBuilder(
                AuthenticatedUserCriteria.hasUsername(username),
                AuthenticatedUserCriteria.hasName(name),
                AuthenticatedUserCriteria.hasSurname(surname)
        );
    }
    @GetMapping("/participants/contests/{idContest}/{idUser}")
    public ResponseEntity<?> getParticipant(@PathVariable("idContest") UUID idContest, @PathVariable("idUser") UUID idParticipant, @RequestParam UUID idUser) {
        if (_userService.appropriateUser(idUser, UserPermission.ANIMATOR_CONTEST_PARTICIPANT_READ)) {
            AuthenticatedUserDto userDto = _contestService.getParticipantById(idContest, idParticipant);
            if (userDto != null) {
                return new ResponseEntity<>(userDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @PostMapping("/participants/contests/{idContest}")
    public ResponseEntity<?> addParticipant(@PathVariable("idContest") UUID idContest, @RequestBody AddParticipantRequest request) {
        AuthenticatedUserDto userDto = _userService.findByUsername(request.getUsername());
        if (userDto != null && _userService.appropriateUser(userDto.getId(), UserPermission.AUTHTOURIST_CONTEST_PARTICIPATE)) {
            AuthenticatedUserDto result = _contestService.addParticipant(idContest, userDto);
            if (result != null) {
                return new ResponseEntity<>(result, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @DeleteMapping("/participants/contests/{idContest}")
    public ResponseEntity<?> deleteParticipant(@PathVariable("idContest") UUID idContest, @RequestBody DeleteParticipantRequest request) {
        AuthenticatedUserDto userDto = _userService.findByUsername(request.getUsername());
        if (userDto != null && _userService.appropriateUser(request.getIdUser(), UserPermission.ANIMATOR_CONTEST_PARTICIPANT_DELETE)) {
            AuthenticatedUserDto result = _contestService.deleteParticipant(idContest, userDto);
            if (result != null) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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
    private <T extends POIDto, S extends ModifyContentAsContributionRequest> T modifyPoiAsContributionConfiguration(T poiDto, S request) {
        poiDto.setName(request.getName());
        poiDto.setCoordinates(Coordinates.fromStrings(request.getLatitude(), request.getLongitude()));
        poiDto.setDescription(request.getDescription());
        poiDto.setAuthor(request.getAuthor());
        poiDto.setCreationDate(Date.toDate(LocalDate.now()));
        poiDto.setExpiryDate(Date.fromString(request.getExpiryDate()));
        return poiDto;
    }
    private <T extends ItineraryDto> T modifyItineraryAsContributionConfiguration(T itineraryDto, AddItineraryAsContributionRequest request) {
        itineraryDto.setName(request.getName());
        itineraryDto.setCoordinates(Coordinates.fromStrings(request.getLatitude(), request.getLongitude()));
        itineraryDto.setDescription(request.getDescription());
        itineraryDto.setAuthor(request.getAuthor());
        LocalDate localDate = LocalDate.now();
        itineraryDto.setCreationDate(new Date(localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear()));
        itineraryDto.setExpiryDate(Date.fromString(request.getExpiryDate()));
        for (UUID idPoi : request.getPois()) {
            var poi = _poiService.getPOIById(idPoi);
            var poiDto = GenericPOIMapper.toDto(poi, true);
            if (poiDto != null && poiDto.getState().equals(ContentState.UPLOADED) && !itineraryDto.getItineraryPois().contains(poiDto)) {
                itineraryDto.getItineraryPois().add(poiDto);
            }
        }
        return (itineraryDto.getItineraryPois().size() >= 2) ? itineraryDto : null;
    }
    
}