package it.unicam.cs.idsflsm.municipalplatform.api.controllers.content.itinerary;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidContentStateException;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidDateFormatException;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidLatitudeException;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidLongitudeException;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.content.itinerary.IItineraryService;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.content.poi.IPOIService;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.user.IUserService;
import it.unicam.cs.idsflsm.municipalplatform.application.criterias.content.itinerary.ItineraryCriteria;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.poi.AuthorizedPOIMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.poi.GenericPOIMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AuthorizedAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.PendingAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.AuthorizedItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.ItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.PendingItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.AuthorizedPOIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.POIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedUserDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.attachment.AddAttachmentRequest;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.attachment.ModifyAttachmentRequest;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.content.itinerary.AddItineraryRequest;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.content.itinerary.ModifyItineraryRequest;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.content.itinerary.UpdateItineraryRequest;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.content.itinerary.ValidatePendingItineraryRequest;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.AuthorizedPOI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.*;
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
    private final IPOIService _poiService;
    private final IUserService _userService;
    @GetMapping("/pending")
    public ResponseEntity<?> getAllPendingItineraries
            (@RequestParam(required = true) UUID idUser,
             @RequestParam(required = false) String name,
             @RequestParam(required = false) String latitude,
             @RequestParam(required = false) String longitude,
             @RequestParam(required = false) String description,
             @RequestParam(required = false) String author,
             @RequestParam(required = false) String creationDate,
             @RequestParam(required = false) String expiryDate,
             @RequestParam(required = false) String state) {
        try {
            Predicate<Itinerary> criterias;
            if (_userService.appropriateUser(idUser, UserPermission.TOURIST_CONTENT_READ)) {
                state = "UPLOADED";
            }
            criterias = ItineraryCriteria.isPendingItinerary().and(getAllCriterias(name, latitude, longitude, description, author, creationDate, expiryDate, state));
            List<PendingItineraryDto> pendingItineraryDtos = _itineraryService.getAllPendingItineraries(Optional.of(criterias));
            if (!pendingItineraryDtos.isEmpty()) {
                return new ResponseEntity<>(pendingItineraryDtos, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (InvalidLatitudeException | InvalidLongitudeException | InvalidDateFormatException | InvalidContentStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/authorized")
    public ResponseEntity<?> getAllAuthorizedItineraries
            (@RequestParam(required = true) UUID idUser,
             @RequestParam(required = false) String name,
             @RequestParam(required = false) String latitude,
             @RequestParam(required = false) String longitude,
             @RequestParam(required = false) String description,
             @RequestParam(required = false) String author,
             @RequestParam(required = false) String creationDate,
             @RequestParam(required = false) String expiryDate,
             @RequestParam(required = false) String state) {
        try {
            Predicate<Itinerary> criterias;
            if (_userService.appropriateUser(idUser, UserPermission.TOURIST_CONTENT_READ)) {
                state = "UPLOADED";
            }
            criterias = ItineraryCriteria.isAuthorizedItinerary().and(getAllCriterias(name, latitude, longitude, description, author, creationDate, expiryDate, state));
            List<AuthorizedItineraryDto> authorizedItineraryDtos = _itineraryService.getAllAuthorizedItineraries(Optional.of(criterias));
            if (!authorizedItineraryDtos.isEmpty()) {
                return new ResponseEntity<>(authorizedItineraryDtos, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (InvalidLatitudeException | InvalidLongitudeException | InvalidDateFormatException | InvalidContentStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    private Predicate<Itinerary> getAllCriterias(String name, String latitude, String longitude, String description, String author, String creationDate, String expiryDate, String state) {
        Coordinates c = (latitude != null && longitude != null) ? Coordinates.fromStrings(latitude, longitude) : null;
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
    public ResponseEntity<?> getPendingItinerary(@PathVariable("id") UUID id, @RequestParam UUID idUser) {
        boolean uploaded = true;
        PendingItineraryDto pendingItineraryDto = _itineraryService.getPendingItineraryById(id);
        if (_userService.appropriateUser(idUser, UserPermission.TOURIST_CONTENT_READ)) {
            uploaded =  pendingItineraryDto.getState().equals(ContentState.UPLOADED);
        }
        if (pendingItineraryDto != null && uploaded) {
            return new ResponseEntity<>(pendingItineraryDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
    @GetMapping("/authorized/{id}")
    public ResponseEntity<?> getAuthorizedItinerary(@PathVariable("id") UUID id, @RequestParam UUID idUser) {
        boolean uploaded = true;
        AuthorizedItineraryDto authorizedItineraryDto = _itineraryService.getAuthorizedItineraryById(id);
        if (_userService.appropriateUser(idUser, UserPermission.TOURIST_CONTENT_READ)) {
            uploaded =  authorizedItineraryDto.getState().equals(ContentState.UPLOADED);
        }
        if (authorizedItineraryDto != null && uploaded) {
            return new ResponseEntity<>(authorizedItineraryDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
    @PostMapping("/pending")
    public ResponseEntity<?> addPendingItinerary(@RequestBody AddItineraryRequest request) {
        try {
            if (_userService.appropriateUser(request.getIdUser(), UserPermission.CONTRIBUTOR_CONTENT_CREATE_PENDING)) {
                PendingItineraryDto pendingItineraryDto = new PendingItineraryDto();
                PendingItineraryDto result = _itineraryService.addPendingItinerary(modifyItineraryConfiguration(pendingItineraryDto, request, ContentState.VALIDABLE));
                if (result != null) {
                    return new ResponseEntity<>(result, HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        } catch (InvalidLatitudeException | InvalidLongitudeException | InvalidDateFormatException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/authorized")
    public ResponseEntity<?> addAuthorizedItinerary(@RequestBody AddItineraryRequest request) {
        try {
            if (_userService.appropriateUser(request.getIdUser(), UserPermission.AUTHCONTRIBUTOR_CONTENT_CREATE_AUTHORIZED)) {
                AuthorizedItineraryDto authorizedItineraryDto = new AuthorizedItineraryDto();
                AuthorizedItineraryDto result = _itineraryService.addAuthorizedItinerary(modifyItineraryConfiguration(authorizedItineraryDto, request, ContentState.UPLOADABLE));
                if (result != null) {
                    return new ResponseEntity<>(result, HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        } catch (InvalidLatitudeException | InvalidLongitudeException | InvalidDateFormatException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
//    @DeleteMapping("/pending/{id}")
//    public ResponseEntity<?> deletePendingItinerary(@PathVariable("id") UUID id) {
//        boolean result = _itineraryService.deletePendingItineraryById(id);
//        if (result) {
//            return new ResponseEntity<>(HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//    }
//    @DeleteMapping("/authorized/{id}")
//    public ResponseEntity<?> deleteAuthorizedItinerary(@PathVariable("id") UUID id) {
//        boolean result = _itineraryService.deleteAuthorizedItineraryById(id);
//        if (result) {
//            return new ResponseEntity<>(HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//    }
    @PutMapping("/pending/{id}")
    public ResponseEntity<?> updatePendingItinerary(@PathVariable("id") UUID id, @RequestBody UpdateItineraryRequest request) {
        try {
            if (_userService.appropriateUser(request.getIdUser(), UserPermission.CURATOR_CONTENT_UPDATE)) {
//                PendingItineraryDto existingPendingItinerary = _itineraryService.getPendingItineraryById(id);
                Predicate<Itinerary> predicate = ItineraryCriteria.isInUploadedState().and(itinerary -> itinerary.getId().equals(id));
                PendingItineraryDto existingPendingItinerary = _itineraryService.getAllPendingItineraries(Optional.of(predicate)).get(0);
                if (existingPendingItinerary != null) {
                    PendingItineraryDto result = _itineraryService.updatePendingItinerary(modifyItineraryConfiguration(existingPendingItinerary, request, ContentState.UPLOADABLE));
                    if (result != null) {
                        return new ResponseEntity<>(result, HttpStatus.OK);
                    } else {
                        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                    }
                } else {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        } catch (InvalidLatitudeException | InvalidLongitudeException | InvalidDateFormatException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/authorized/{id}")
    public ResponseEntity<?> updateAuthorizedItinerary(@PathVariable("id") UUID id, @RequestBody UpdateItineraryRequest request) {
        try {
            if (_userService.appropriateUser(request.getIdUser(), UserPermission.CURATOR_CONTENT_UPDATE)) {
//                AuthorizedItineraryDto existingAuthorizedItinerary = _itineraryService.getAuthorizedItineraryById(id);
                Predicate<Itinerary> predicate = ItineraryCriteria.isInUploadedState().and(itinerary -> itinerary.getId().equals(id));
                AuthorizedItineraryDto existingAuthorizedItinerary = _itineraryService.getAllAuthorizedItineraries(Optional.of(predicate)).get(0);
                if (existingAuthorizedItinerary != null) {
                    AuthorizedItineraryDto result = _itineraryService.updateAuthorizedItinerary(modifyItineraryConfiguration(existingAuthorizedItinerary, request, ContentState.UPLOADABLE));
                    if (result != null) {
                        return new ResponseEntity<>(result, HttpStatus.OK);
                    } else {
                        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                    }
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        } catch (InvalidLatitudeException | InvalidLongitudeException | InvalidDateFormatException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    private <T extends ItineraryDto, S extends ModifyItineraryRequest> T modifyItineraryConfiguration(T itineraryDto, S request, ContentState state) {
        itineraryDto.setName(request.getName());
        itineraryDto.setCoordinates(Coordinates.fromStrings(request.getLatitude(), request.getLongitude()));
        itineraryDto.setDescription(request.getDescription());
        itineraryDto.setAuthor(request.getAuthor());
        itineraryDto.setCreationDate(Date.toDate(LocalDate.now()));
        itineraryDto.setExpiryDate(Date.fromString(request.getExpiryDate()));
        itineraryDto.setState(state);
        for (UUID idPoi : request.getPois()) {
            POI poi = _poiService.getPOIById(idPoi);
            POIDto poiDto = GenericPOIMapper.toDto(poi, true);
            if (poiDto != null && poiDto.getState().equals(ContentState.UPLOADED) && !itineraryDto.getItineraryPois().contains(poiDto)) {
                itineraryDto.getItineraryPois().add(poiDto);
            }
        }
        return (itineraryDto.getItineraryPois().size() >= 2) ? itineraryDto : null;
    }
    @PutMapping("/pending/validable/{id}")
    public ResponseEntity<?> validatePendingItinerary(@PathVariable("id") UUID id, @RequestBody ValidatePendingItineraryRequest request) {
        if (_userService.appropriateUser(request.getIdUser(), UserPermission.CURATOR_CONTENT_VALIDATE)) {
            Predicate<Itinerary> predicate = itinerary -> itinerary.getId().equals(id);
            PendingItineraryDto result = _itineraryService.validatePendingItinerary(Optional.of(predicate), request.isValidate());
            if (result != null) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @PutMapping("/pending/save/{id}")
    public ResponseEntity<?> savePendingItinerary(@PathVariable UUID id, @RequestParam UUID idUser) {
        if (_userService.appropriateUser(idUser, UserPermission.AUTHTOURIST_CONTENT_SAVE)) {
            AuthenticatedUserDto userDto = _userService.findById(idUser);
            PendingItineraryDto result = _itineraryService.savePendingItinerary(id, userDto);
            if (result != null) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @PutMapping("/authorized/save/{id}")
    public ResponseEntity<?> saveAuthorizedItinerary(@PathVariable UUID id, @RequestParam UUID idUser) {
        if (_userService.appropriateUser(idUser, UserPermission.AUTHTOURIST_CONTENT_SAVE)) {
            AuthenticatedUserDto userDto = _userService.findById(idUser);
            AuthorizedItineraryDto result = _itineraryService.saveAuthorizedItinerary(id, userDto);
            if (result != null) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @PatchMapping("/pending/uploadable")
    public ResponseEntity<?> uploadAllPendingItineraries(@RequestParam UUID idUser) {
        if (_userService.appropriateUser(idUser, UserPermission.AUTHCONTRIBUTOR_CONTENT_UPLOAD)) {
            List<PendingItineraryDto> result = _itineraryService.uploadAllPendingItineraries();
            if (result != null) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @PatchMapping("/pending/uploadable/{id}")
    public ResponseEntity<?> uploadPendingItinerary(@PathVariable("id") UUID id, @RequestParam UUID idUser) {
        if (_userService.appropriateUser(idUser, UserPermission.AUTHCONTRIBUTOR_CONTENT_UPLOAD)) {
            PendingItineraryDto itineraryDto = _itineraryService.uploadPendingItinerary(id);
            if (itineraryDto != null) {
                return new ResponseEntity<>(itineraryDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @PatchMapping("/authorized/uploadable")
    public ResponseEntity<?> uploadAllAuthorizedItineraries(@RequestParam UUID idUser) {
        if (_userService.appropriateUser(idUser, UserPermission.AUTHCONTRIBUTOR_CONTENT_UPLOAD)) {
            List<AuthorizedItineraryDto> result = _itineraryService.uploadAllAuthorizedItineraries();
            if (result != null) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @PatchMapping("/authorized/uploadable/{id}")
    public ResponseEntity<?> uploadAuthorizedItinerary(@PathVariable("id") UUID id, @RequestParam UUID idUser) {
        if (_userService.appropriateUser(idUser, UserPermission.AUTHCONTRIBUTOR_CONTENT_UPLOAD)) {
            AuthorizedItineraryDto itineraryDto = _itineraryService.uploadAuthorizedItinerary(id);
            if (itineraryDto != null) {
                return new ResponseEntity<>(itineraryDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @PostMapping("/attachments/pending/{id}")
    public ResponseEntity<?> addPendingAttachment(@PathVariable("id") UUID id, @RequestBody AddAttachmentRequest request) {
        if (_userService.appropriateUser(request.getIdUser(), UserPermission.AUTHTOURIST_ATTACHMENT_CREATE)) {
            try {
                Optional<Predicate<Itinerary>> predicate = Optional.of(itinerary -> itinerary.getId().equals(id));
                PendingAttachmentDto attachmentDto = new PendingAttachmentDto();
                AttachmentDto result = _itineraryService.addPendingAttachment(modifyAttachmentConfiguration(attachmentDto, request), predicate);
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
    @PostMapping("/attachments/authorized/{id}")
    public ResponseEntity<?> addAuthorizedAttachment(@PathVariable("id") UUID id, @RequestBody AddAttachmentRequest request) {
        if (_userService.appropriateUser(request.getIdUser(), UserPermission.AUTHCONTRIBUTOR_ATTACHMENT_CREATE_AUTHORIZED)) {
            try {
                Optional<Predicate<Itinerary>> predicate = Optional.of(itinerary -> itinerary.getId().equals(id));
                AuthorizedAttachmentDto attachmentDto = new AuthorizedAttachmentDto();
                AttachmentDto result = _itineraryService.addAuthorizedAttachment(modifyAttachmentConfiguration(attachmentDto, request), predicate);
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
    private <T extends AttachmentDto, S extends ModifyAttachmentRequest> T modifyAttachmentConfiguration(T attachmentDto, S request) {
        attachmentDto.setName(request.getName());
        attachmentDto.setDescription(request.getDescription());
        attachmentDto.setAuthor(request.getAuthor());
        attachmentDto.setCreationDate(Date.toDate(LocalDate.now()));
        attachmentDto.setExpiryDate(Date.fromString(request.getExpiryDate()));
        return attachmentDto;
    }
}
