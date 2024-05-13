package it.unicam.cs.idsflsm.municipalplatform.api.controllers.contest;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidDateFormatException;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidLatitudeException;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidLongitudeException;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.content.itinerary.IItineraryService;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.content.poi.IPOIService;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.contest.IContestService;
import it.unicam.cs.idsflsm.municipalplatform.application.criterias.contest.ContestCriteria;
import it.unicam.cs.idsflsm.municipalplatform.application.criterias.user.AuthenticatedTouristCriteria;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.itinerary.PendingItineraryMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.poi.PendingPOIMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.contest.ContestDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.contest.ContributionDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.ItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.PendingItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.POIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.PendingPOIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedTouristDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.contest.*;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contest;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contribution;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedTourist;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContestResult;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Coordinates;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;
import jakarta.validation.Valid;
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
            return new ResponseEntity<>(contestDtos, HttpStatus.OK);
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
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping
    public ResponseEntity<?> addContest(@Valid @RequestBody AddContestRequest request) {
        try {
            ContestDto contestDto = new ContestDto();
            boolean result = _contestService.addContest(modifyReportConfiguration(contestDto, request, null));
            if (result) {
                return new ResponseEntity<>(contestDto, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (InvalidDateFormatException e1) {
            return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContest(@PathVariable("id") UUID id) {
        boolean result = _contestService.deleteContestById(id);
        if (result) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/contributions/contests/{idContest}")
    public ResponseEntity<?> getAllContributions(@PathVariable(value = "idContest", required = false) UUID idContest) {
        Optional<Predicate<Contribution>> predicate = (idContest != null)
                ? (Optional.of(contribution -> contribution.getContest().getId().equals(idContest))) : Optional.empty();
        List<ContributionDto> contributionDtos = _contestService.getAllContributions(predicate);
        if (!contributionDtos.isEmpty()) {
            return new ResponseEntity<>(contributionDtos, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/contributions/{idContribution}/")
    public ResponseEntity<?> getContribution(@PathVariable("idContribution") UUID idContribution) {
        ContributionDto contributionDto = _contestService.getContributionById(idContribution);
        if (contributionDto != null) {
            return new ResponseEntity<>(contributionDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/contributions/poi/contests/{idContest}")
    public ResponseEntity<?> addPOIAsContribution(@PathVariable("idContest") UUID idContest, AddPOIAsContributionRequest request) {
        try {
            PendingPOIDto poiDto = new PendingPOIDto();
            ContributionDto contributionDto = new ContributionDto();
            contributionDto.setPoi(PendingPOIMapper.toEntity(modifyPoiAsContributionConfiguration(poiDto, request, null)));
            boolean result = _contestService.addContribution(idContest, contributionDto);
            if (result) {
                return new ResponseEntity<>(contributionDto, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (InvalidLatitudeException | InvalidLongitudeException | InvalidDateFormatException e1) {
            return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/contributions/itinerary/contests/{idContest}")
    public ResponseEntity<?> addItineraryAsContribution(@PathVariable("idContest") UUID idContest, AddItineraryAsContributionRequest request) {
        try {
            PendingItineraryDto itineraryDto = new PendingItineraryDto();
            ContributionDto contributionDto = new ContributionDto();
            contributionDto.setItinerary(PendingItineraryMapper.toEntity(modifyItineraryAsContributionConfiguration(itineraryDto, request, null)));
            boolean result = _contestService.addContribution(idContest, contributionDto);
            if (result) {
                return new ResponseEntity<>(contributionDto, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (InvalidLatitudeException | InvalidLongitudeException | InvalidDateFormatException e1) {
            return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/contributions/{id}")
    public ResponseEntity<?> deleteContribution(@PathVariable("id") UUID id) {
        boolean result = _contestService.deleteContributionById(id);
        if (result) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/contributions/validable/{id}")
    public ResponseEntity<?> validateContribution(@PathVariable("id") UUID id, @RequestParam boolean validate) {
        ContributionDto contributionDto = _contestService.getContributionById(id);
        Optional<Predicate<Contribution>> predicate = Optional.of(contribution -> contribution.getId().equals(id));
        if (contributionDto != null) {
            boolean result = _contestService.validateContribution(contributionDto, predicate, validate);
            if (result) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PatchMapping("/contributions/uploadable/{idContribution}")
    public ResponseEntity<?> uploadContribution(@PathVariable("idContribution") UUID idContribution) {
        ContributionDto contributionDto = _contestService.getContributionById(idContribution);
        if (contributionDto.getResult() == ContestResult.WINNER) {
            Optional<Contribution> result = _contestService.uploadContribution(contributionDto);
            if (result.isPresent()) {
                if (result.get().getPoi() != null && result.get().getItinerary() == null) {
                    _poiService.saveInRepository(result.get().getPoi());
                    return new ResponseEntity<>(HttpStatus.OK);
                }
                if (result.get().getPoi() == null && result.get().getItinerary() != null) {
                    _itineraryService.saveInRepository(result.get().getItinerary());
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/contributions/winners/{idContest}")
    public ResponseEntity<?> getWinnerContribution(@PathVariable("idContest") UUID idContest) {
        ContributionDto contributionDto = _contestService.getWinnerContribution(idContest);
        if (contributionDto != null) {
            return new ResponseEntity<>(contributionDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/contributions/losers/{idContest}")
    public ResponseEntity<?> deleteAllLoserContributions(@PathVariable(value = "idContest", required = false) UUID idContest) {
        Optional<UUID> id = (idContest != null) ? Optional.of(idContest) : Optional.empty();
        boolean result = _contestService.deleteAllLoserContributions(id);
        if (result) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/participants/contests/{idContest}")
    public ResponseEntity<?> getAllParticipants
            (@PathVariable("idContest") UUID idContest,
             @RequestParam(required = false) String username,
             @RequestParam(required = false) String name,
             @RequestParam(required = false) String surname) {
        Predicate<AuthenticatedTourist> criterias = getAllCriterias(username, name, surname);
        List<AuthenticatedTouristDto> authenticatedTouristDtos = _contestService.getAllParticipants(idContest, Optional.of(criterias));
        if (!authenticatedTouristDtos.isEmpty()) {
            return new ResponseEntity<>(authenticatedTouristDtos, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    private Predicate<AuthenticatedTourist> getAllCriterias(String username, String name, String surname) {
        return AuthenticatedTouristCriteria.criteriaBuilder(
                AuthenticatedTouristCriteria.hasUsername(username),
                AuthenticatedTouristCriteria.hasName(name),
                AuthenticatedTouristCriteria.hasSurname(surname)
        );
    }
    @GetMapping("/participants/contests/{idContest}/{idUser}")
    public ResponseEntity<?> getParticipant(@PathVariable("idContest") UUID idContest, @PathVariable("idUser") UUID idUser) {
        AuthenticatedTouristDto authenticatedTouristDto = _contestService.getParticipantById(idContest, idUser);
        if (authenticatedTouristDto != null) {
            return new ResponseEntity<>(authenticatedTouristDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/participants/contests/{idContest}")
    public ResponseEntity<?> addParticipant(@PathVariable("idContest") UUID idContest, @Valid @RequestBody AddParticipantRequest request) {
        Predicate<AuthenticatedTourist> predicate = getAllCriterias(request.getUsername(), request.getName(), request.getSurname());
        AuthenticatedTouristDto authenticatedTouristDto = _contestService.getAllParticipants(idContest, Optional.of(predicate)).get(0);
        if (authenticatedTouristDto != null) {
            boolean result = _contestService.addParticipant(idContest, authenticatedTouristDto);
            if (result) {
                return new ResponseEntity<>(HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/participants/{idContest}")
    public ResponseEntity<?> deleteParticipant(@PathVariable("idContest") UUID idContest, @Valid @RequestBody DeleteParticipantRequest request) {
        Predicate<AuthenticatedTourist> predicate = getAllCriterias(request.getUsername(), request.getName(), request.getSurname());
        AuthenticatedTouristDto authenticatedTouristDto = _contestService.getAllParticipants(idContest, Optional.of(predicate)).get(0);
        if (authenticatedTouristDto != null) {
            boolean result = _contestService.deleteParticipant(idContest, authenticatedTouristDto);
            if (result) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    private <T extends ModifyContestRequest> ContestDto modifyReportConfiguration(ContestDto contestDto, T request, UUID id) {
        contestDto.setId((id != null) ? id : UUID.randomUUID());
        contestDto.setName(request.getName());
        contestDto.setAuthor(request.getAuthor());
        contestDto.setDescription(request.getDescription());
        LocalDate localDate = LocalDate.now();
        contestDto.setCreationDate(new Date(localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear()));
        contestDto.setExpiryDate(Date.fromString(request.getExpiryDate()));
        return contestDto;
    }
    private <T extends POIDto, S extends ModifyContentAsContributionRequest> T modifyPoiAsContributionConfiguration(T poiDto, S request, UUID id) {
        poiDto.setId((id != null) ? id : UUID.randomUUID());
        poiDto.setName(request.getName());
        poiDto.setCoordinates(Coordinates.fromStrings(request.getLatitude(), request.getLongitude()));
        poiDto.setDescription(request.getDescription());
        poiDto.setAuthor(request.getAuthor());
        LocalDate localDate = LocalDate.now();
        poiDto.setCreationDate(new Date(localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear()));
        poiDto.setExpiryDate(Date.fromString(request.getExpiryDate()));
        return poiDto;
    }
    private <T extends ItineraryDto, S extends ModifyContentAsContributionRequest> T modifyItineraryAsContributionConfiguration(T itineraryDto, S request, UUID id) {
        itineraryDto.setId((id != null) ? id : UUID.randomUUID());
        itineraryDto.setName(request.getName());
        itineraryDto.setCoordinates(Coordinates.fromStrings(request.getLatitude(), request.getLongitude()));
        itineraryDto.setDescription(request.getDescription());
        itineraryDto.setAuthor(request.getAuthor());
        LocalDate localDate = LocalDate.now();
        itineraryDto.setCreationDate(new Date(localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear()));
        itineraryDto.setExpiryDate(Date.fromString(request.getExpiryDate()));
        return itineraryDto;
    }
}