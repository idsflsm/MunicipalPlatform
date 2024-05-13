package it.unicam.cs.idsflsm.municipalplatform.api.controllers.itinerary;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidContentStateException;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidDateFormatException;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidLatitudeException;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidLongitudeException;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.content.itinerary.IItineraryService;
import it.unicam.cs.idsflsm.municipalplatform.application.criterias.content.itinerary.ItineraryCriteria;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AuthorizedAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.PendingAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.AuthorizedItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.ItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.PendingItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.attachment.AddAttachmentRequest;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.attachment.AddAttachmentRequest;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.attachment.ModifyAttachmentRequest;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.content.itinerary.AddItineraryRequest;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.content.itinerary.ModifyItineraryRequest;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.content.itinerary.UpdateItineraryRequest;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Coordinates;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
@RestController
@RequestMapping("/api/itineraries")
@AllArgsConstructor(onConstructor_ = @Autowired)
public class ItineraryController {
    private final IItineraryService _itineraryService;
    @GetMapping("/pending")
    public ResponseEntity<?> getAllPendingItineraries
            (@RequestParam(required = false) String name,
             @RequestParam(required = false) String latitude,
             @RequestParam(required = false) String longitude,
             @RequestParam(required = false) String description,
             @RequestParam(required = false) String author,
             @RequestParam(required = false) String creationDate,
             @RequestParam(required = false) String expiryDate,
             @RequestParam(required = false) String state) {
        try {
            Predicate<Itinerary> criterias = ItineraryCriteria.isPendingItinerary()
                    .and(getAllCriterias(name, latitude, longitude, description, author, creationDate, expiryDate, state));
            List<PendingItineraryDto> pendingItineraryDtos =
                    _itineraryService.getAllPendingItineraries(Optional.of(criterias));
            return new ResponseEntity<>(pendingItineraryDtos, HttpStatus.OK);
        } catch (InvalidLatitudeException | InvalidLongitudeException | InvalidDateFormatException | InvalidContentStateException e1) {
            return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/authorized")
    public ResponseEntity<?> getAllAuthorizedItineraries
            (@RequestParam(required = false) String name,
             @RequestParam(required = false) String latitude,
             @RequestParam(required = false) String longitude,
             @RequestParam(required = false) String description,
             @RequestParam(required = false) String author,
             @RequestParam(required = false) String creationDate,
             @RequestParam(required = false) String expiryDate,
             @RequestParam(required = false) String state) {
        try {
            Predicate<Itinerary> criterias = ItineraryCriteria.isAuthorizedItinerary()
                    .and(getAllCriterias(name, latitude, longitude, description, author, creationDate, expiryDate, state));
            List<AuthorizedItineraryDto> authorizedItineraryDtos =
                    _itineraryService.getAllAuthorizedItineraries(Optional.of(criterias));
            return new ResponseEntity<>(authorizedItineraryDtos, HttpStatus.OK);
        } catch (InvalidLatitudeException | InvalidLongitudeException | InvalidDateFormatException | InvalidContentStateException e1) {
            return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    private Predicate<Itinerary> getAllCriterias(String name, String latitude, String longitude, String description, String author, String creationDate, String expiryDate, String state) {
        Coordinates c = Coordinates.fromStrings(latitude, longitude);
        Date cd = (creationDate != null) ? Date.fromString(creationDate) : null;
        Date ed = (expiryDate != null) ? Date.fromString(expiryDate) : null;
        ContentState cs = (state != null) ? ContentState.fromString(state) : null;
        return ItineraryCriteria.criteriaBuilder(
                ItineraryCriteria.hasName(name),
                ItineraryCriteria.hasCoordinates(c),
                ItineraryCriteria.hasDescription(description),
                ItineraryCriteria.hasAuthor(author),
                ItineraryCriteria.hasCreationDate(cd),
                ItineraryCriteria.hasExpiryDate(ed),
                ItineraryCriteria.hasState(cs)
        );
    }
    @GetMapping("/pending/{id}")
    public ResponseEntity<?> getPendingItinerary(@PathVariable("id") UUID id) {
        PendingItineraryDto pendingItineraryDto = _itineraryService.getPendingItineraryById(id);
        if (pendingItineraryDto != null) {
            return new ResponseEntity<>(pendingItineraryDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/authorized/{id}")
    public ResponseEntity<?> getAuthorizedItinerary(@PathVariable("id") UUID id) {
        AuthorizedItineraryDto authorizedItineraryDto = _itineraryService.getAuthorizedItineraryById(id);
        if (authorizedItineraryDto != null) {
            return new ResponseEntity<>(authorizedItineraryDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/pending")
    public ResponseEntity<?> addPendingItinerary(@Valid @RequestBody AddItineraryRequest request) {
        try {
            PendingItineraryDto pendingItineraryDto = new PendingItineraryDto();
            boolean result = _itineraryService.addPendingItinerary(modifyItineraryConfiguration(pendingItineraryDto, request, null));
            if (result) {
                return new ResponseEntity<>(pendingItineraryDto, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (InvalidLatitudeException | InvalidLongitudeException | InvalidDateFormatException e1) {
            return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/authorized")
    public ResponseEntity<?> addAuthorizedItinerary(@Valid @RequestBody AddItineraryRequest request) {
        try {
            AuthorizedItineraryDto authorizedItineraryDto = new AuthorizedItineraryDto();
            boolean result = _itineraryService.addAuthorizedItinerary(modifyItineraryConfiguration(authorizedItineraryDto, request, null));
            if (result) {
                return new ResponseEntity<>(authorizedItineraryDto, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (InvalidLatitudeException | InvalidLongitudeException | InvalidDateFormatException  e1) {
            return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/pending/{id}")
    public ResponseEntity<?> deletePendingItinerary(@PathVariable("id") UUID id) {
        boolean result = _itineraryService.deletePendingItineraryById(id);
        if (result) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/authorized/{id}")
    public ResponseEntity<?> deleteAuthorizedItinerary(@PathVariable("id") UUID id) {
        boolean result = _itineraryService.deleteAuthorizedItineraryById(id);
        if (result) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/pending/{id}")
    public ResponseEntity<?> updatePendingItinerary(@PathVariable("id") UUID id, @Valid @RequestBody UpdateItineraryRequest request) {
        PendingItineraryDto existingPendingItinerary = _itineraryService.getPendingItineraryById(id);
        if (existingPendingItinerary != null) {
            try {
                boolean result = _itineraryService.updatePendingItinerary(modifyItineraryConfiguration(existingPendingItinerary, request, id), Optional.empty());
                if (result) {
                    return new ResponseEntity<>(existingPendingItinerary, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            } catch (InvalidLatitudeException | InvalidLongitudeException | InvalidDateFormatException e1) {
                return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/authorized/{id}")
    public ResponseEntity<?> updateAuthorizedItinerary(@PathVariable("id") UUID id, @Valid @RequestBody UpdateItineraryRequest request) {
        AuthorizedItineraryDto existingAuthorizedItinerary = _itineraryService.getAuthorizedItineraryById(id);
        if (existingAuthorizedItinerary != null) {
            try {
                boolean result = _itineraryService.updateAuthorizedItinerary(modifyItineraryConfiguration(existingAuthorizedItinerary, request, id), Optional.empty());
                if (result) {
                    return new ResponseEntity<>(existingAuthorizedItinerary, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            } catch (InvalidLatitudeException | InvalidLongitudeException | InvalidDateFormatException e1) {
                return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    private <T extends ItineraryDto, S extends ModifyItineraryRequest> T modifyItineraryConfiguration(T itineraryDto, S request, UUID id) {
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
    @PutMapping("/pending/validable/{id}")
    public ResponseEntity<?> validatePendingItinerary(@PathVariable("id") UUID id, @Valid @RequestBody boolean validate) {
        PendingItineraryDto itineraryDto = _itineraryService.getPendingItineraryById(id);
        Optional<Predicate<Itinerary>> predicate = Optional.of(itinerary -> itinerary.getId().equals(id));
        if (itineraryDto != null) {
            boolean result = _itineraryService.validatePendingItinerary(itineraryDto, predicate, validate);
            if (result) {
                return new ResponseEntity<>(itineraryDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/pending/save/{id}")
    public ResponseEntity<?> savePendingItinerary(@PathVariable UUID id, UUID idAuthenticatedUser) {
        boolean result = _itineraryService.savePendingItinerary(id, idAuthenticatedUser);
        if (result) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/authorized/save/{id}")
    public ResponseEntity<?> saveAuthorizedItinerary(@PathVariable UUID id, UUID idAuthenticatedUser) {
        boolean result = _itineraryService.saveAuthorizedItinerary(id, idAuthenticatedUser);
        if (result) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/attachments/pending/{id}")
    public ResponseEntity<?> addPendingAttachment(@PathVariable("id") UUID id, @Valid @RequestBody AddAttachmentRequest request) {
        try {
            Optional<Predicate<Itinerary>> predicate = Optional.of(itinerary -> itinerary.getId().equals(id));
            PendingAttachmentDto attachmentDto = new PendingAttachmentDto();
            boolean result = _itineraryService.addPendingAttachment(modifyAttachmentConfiguration(attachmentDto, request, null), predicate);
            if (result) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (InvalidDateFormatException e1) {
            return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/attachments/authorized/{id}")
    public ResponseEntity<?> addAuthorizedAttachment(@PathVariable("id") UUID id, @Valid @RequestBody AddAttachmentRequest request) {
        try {
            Optional<Predicate<Itinerary>> predicate = Optional.of(itinerary -> itinerary.getId().equals(id));
            AuthorizedAttachmentDto attachmentDto = new AuthorizedAttachmentDto();
            boolean result = _itineraryService.addAuthorizedAttachment(modifyAttachmentConfiguration(attachmentDto, request, id), predicate);
            if (result) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (InvalidDateFormatException e1) {
            return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    private <T extends AttachmentDto, S extends ModifyAttachmentRequest> T modifyAttachmentConfiguration(T attachmentDto, S request, UUID id) {
        attachmentDto.setId((id != null) ? id : UUID.randomUUID());
        attachmentDto.setName(request.getName());
        attachmentDto.setDescription(request.getDescription());
        attachmentDto.setAuthor(request.getAuthor());
        attachmentDto.setExpiryDate(Date.fromString(request.getExpiryDate()));
        return attachmentDto;
    }
}
