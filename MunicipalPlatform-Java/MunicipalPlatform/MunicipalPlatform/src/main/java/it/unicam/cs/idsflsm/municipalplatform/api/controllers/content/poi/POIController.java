package it.unicam.cs.idsflsm.municipalplatform.api.controllers.content.poi;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidContentStateException;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidDateFormatException;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidLatitudeException;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidLongitudeException;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.content.poi.IPOIService;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.user.IUserService;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.validators.ValidUUID;
import it.unicam.cs.idsflsm.municipalplatform.application.criterias.content.poi.POICriteria;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.itinerary.GenericItineraryMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AuthorizedAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.PendingAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.AuthorizedPOIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.POIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.PendingPOIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedUserDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.attachment.AddAttachmentRequest;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.attachment.ModifyAttachmentRequest;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.content.poi.AddPOIRequest;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.content.poi.ModifyPOIRequest;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.content.poi.UpdatePOIRequest;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.content.poi.ValidatePendingPOIRequest;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Coordinates;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserPermission;
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
@RequestMapping("/api/pois")
@AllArgsConstructor(onConstructor_ = @Autowired)
public class POIController {
    private final IPOIService _poiService;
    private final IUserService _userService;
    @GetMapping("/pending")
    public ResponseEntity<?> getAllPendingPOIs
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
            Predicate<POI> criterias;
            if (_userService.appropriateUser(idUser, UserPermission.TOURIST_CONTENT_READ)) {
                state = "UPLOADED";
            }
            String finalState = state;
            criterias = POICriteria.isPendingPoi().and(getAllCriterias(name, latitude, longitude, description, author, creationDate, expiryDate, state));
            List<PendingPOIDto> pendingPOIDtos = _poiService.getAllPendingPOIs(Optional.of(criterias));
            pendingPOIDtos.forEach(pendingPOIDto -> {
                pendingPOIDto = (PendingPOIDto) pendingPOIDto.allWithState(ContentState.fromString(finalState));
            });
            if (!pendingPOIDtos.isEmpty()) {
                return new ResponseEntity<>(pendingPOIDtos, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (InvalidLatitudeException | InvalidLongitudeException | InvalidDateFormatException | InvalidContentStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
    @GetMapping("/authorized")
    public ResponseEntity<?> getAllAuthorizedPOIs
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
            Predicate<POI> criterias;
            if (_userService.appropriateUser(idUser, UserPermission.TOURIST_CONTENT_READ)) {
                state = "UPLOADED";
            }
            String finalState = state;
            criterias = POICriteria.isAuthorizedPoi().and(getAllCriterias(name, latitude, longitude, description, author, creationDate, expiryDate, state));
            List<AuthorizedPOIDto> authorizedPOIDtos = _poiService.getAllAuthorizedPOIs(Optional.of(criterias));
            authorizedPOIDtos.forEach(authorizedPOIDto -> {
                authorizedPOIDto = (AuthorizedPOIDto) authorizedPOIDto.allWithState(ContentState.fromString(finalState));
            });
            if (!authorizedPOIDtos.isEmpty()) {
                return new ResponseEntity<>(authorizedPOIDtos, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (InvalidLatitudeException | InvalidLongitudeException | InvalidDateFormatException | InvalidContentStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    private Predicate<POI> getAllCriterias(String name, String latitude, String longitude, String description, String author, String creationDate, String expiryDate, String state) {
        Coordinates c = Coordinates.fromStrings(latitude, longitude);
        Date cd = (creationDate != null) ? Date.fromString(creationDate) : null;
        Date ed = (expiryDate != null) ? Date.fromString(expiryDate) : null;
        ContentState cs = (state != null) ? ContentState.fromString(state) : null;
        return POICriteria.criteriaBuilder(
                POICriteria.hasName(name),
                POICriteria.hasCoordinates(c),
                POICriteria.hasDescription(description),
                POICriteria.hasAuthor(author),
                POICriteria.hasCreationDate(cd),
                POICriteria.hasExpiryDate(ed),
                POICriteria.hasState(cs)
        );
    }
    @GetMapping("/pending/{id}")
    public ResponseEntity<?> getPendingPOI(@PathVariable("id") UUID id, @RequestParam UUID idUser) {
        boolean uploaded = true;
        PendingPOIDto pendingPOIDto = _poiService.getPendingPOIById(id);
        if (_userService.appropriateUser(idUser, UserPermission.TOURIST_CONTENT_READ)) {
            uploaded =  pendingPOIDto.getState().equals(ContentState.UPLOADED);
            pendingPOIDto = (PendingPOIDto) pendingPOIDto.allWithState(ContentState.UPLOADED);
        }
        if (pendingPOIDto != null && uploaded) {
            return new ResponseEntity<>(pendingPOIDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
    @GetMapping("/authorized/{id}")
    public ResponseEntity<?> getAuthorizedPOI(@PathVariable("id") UUID id, @RequestParam UUID idUser) {
        boolean uploaded = true;
        AuthorizedPOIDto authorizedPOIDto = _poiService.getAuthorizedPOIById(id);
        if (_userService.appropriateUser(idUser, UserPermission.TOURIST_CONTENT_READ)) {
            uploaded =  authorizedPOIDto.getState().equals(ContentState.UPLOADED);
            authorizedPOIDto = (AuthorizedPOIDto) authorizedPOIDto.allWithState(ContentState.UPLOADED);
        }
        if (authorizedPOIDto != null && uploaded) {
            return new ResponseEntity<>(authorizedPOIDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
    @PostMapping("/pending")
    public ResponseEntity<?> addPendingPoi(@RequestBody AddPOIRequest request) {
        try {
            if (_userService.appropriateUser(request.getIdUser(), UserPermission.CONTRIBUTOR_CONTENT_CREATE_PENDING)) {
                PendingPOIDto pendingPoiDto = new PendingPOIDto();
                PendingPOIDto result = _poiService.addPendingPOI(modifyPoiConfiguration(pendingPoiDto, request, ContentState.VALIDABLE));
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
    public ResponseEntity<?> addAuthorizedPoi(@RequestBody AddPOIRequest request) {
        try {
            if (_userService.appropriateUser(request.getIdUser(), UserPermission.AUTHCONTRIBUTOR_CONTENT_CREATE_AUTHORIZED)) {
                AuthorizedPOIDto authorizedPoiDto = new AuthorizedPOIDto();
                AuthorizedPOIDto result = _poiService.addAuthorizedPOI(modifyPoiConfiguration(authorizedPoiDto, request, ContentState.UPLOADABLE));
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
//    public ResponseEntity<?> deletePendingPoi(@PathVariable("id") UUID id) {
//        boolean result = _poiService.deletePendingPOIById(id);
//        if (result) {
//            return new ResponseEntity<>(HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
    @DeleteMapping("/authorized/{id}")
    public ResponseEntity<?> deleteAuthorizedPoi(@PathVariable("id") UUID id) {
        boolean result = _poiService.deleteAuthorizedPOIById(id);
        if (result) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/pending/{id}")
    public ResponseEntity<?> updatePendingPoi(@PathVariable("id") UUID id, @RequestBody UpdatePOIRequest request) {
        try {
            if (_userService.appropriateUser(request.getIdUser(), UserPermission.CURATOR_CONTENT_UPDATE)) {
//                PendingPOIDto existingPendingPoi = _poiService.getPendingPOIById(id);
                Predicate<POI> predicate = POICriteria.isInUploadedState().and(poi -> poi.getId().equals(id));
                PendingPOIDto existingPendingPoi = _poiService.getAllPendingPOIs(Optional.of(predicate)).get(0);
                if (existingPendingPoi != null) {
                    PendingPOIDto result = _poiService.updatePendingPOI(modifyPoiConfiguration(existingPendingPoi, request, ContentState.UPLOADABLE));
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
    @PutMapping("/authorized/{id}")
    public ResponseEntity<?> updateAuthorizedPoi(@PathVariable("id") UUID id, @RequestBody UpdatePOIRequest request) {
        try {
            if (_userService.appropriateUser(request.getIdUser(), UserPermission.CURATOR_CONTENT_UPDATE)) {
//                AuthorizedPOIDto existingAuthorizedPoi = _poiService.getAuthorizedPOIById(id);
                Predicate<POI> predicate = POICriteria.isInUploadedState().and(poi -> poi.getId().equals(id));
                AuthorizedPOIDto existingAuthorizedPoi = _poiService.getAllAuthorizedPOIs(Optional.of(predicate)).get(0);
                if (existingAuthorizedPoi != null) {
                    AuthorizedPOIDto result = _poiService.updateAuthorizedPOI(modifyPoiConfiguration(existingAuthorizedPoi, request, ContentState.UPLOADABLE));
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
    private <T extends POIDto, S extends ModifyPOIRequest> T modifyPoiConfiguration(T poiDto, S request, ContentState state) {
        poiDto.setName(request.getName());
        poiDto.setCoordinates(Coordinates.fromStrings(request.getLatitude(), request.getLongitude()));
        poiDto.setDescription(request.getDescription());
        poiDto.setAuthor(request.getAuthor());
        poiDto.setCreationDate(Date.toDate(LocalDate.now()));
        poiDto.setExpiryDate(Date.fromString(request.getExpiryDate()));
        poiDto.setState(state);
        return poiDto;
    }
    @PutMapping("/pending/validable/{id}")
    public ResponseEntity<?> validatePendingPOI(@PathVariable("id") UUID id, @RequestBody ValidatePendingPOIRequest request) {
        if (_userService.appropriateUser(request.getIdUser(), UserPermission.CURATOR_CONTENT_VALIDATE)) {
            Optional<Predicate<POI>> predicate = Optional.of(poi -> poi.getId().equals(id));
            PendingPOIDto result = _poiService.validatePendingPOI(predicate, request.isValidate());
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
    public ResponseEntity<?> savePendingPoi(@PathVariable UUID id, @RequestParam UUID idUser) {
        if (_userService.appropriateUser(idUser, UserPermission.AUTHTOURIST_CONTENT_SAVE)) {
            AuthenticatedUserDto userDto = _userService.findById(idUser);
            PendingPOIDto result = _poiService.savePendingPOI(id, userDto);
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
    public ResponseEntity<?> saveAuthorizedPoi(@PathVariable UUID id, @RequestParam UUID idUser) {
        if (_userService.appropriateUser(idUser, UserPermission.AUTHTOURIST_CONTENT_SAVE)) {
            AuthenticatedUserDto userDto = _userService.findById(idUser);
            AuthorizedPOIDto result = _poiService.saveAuthorizedPOI(id, userDto);
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
    public ResponseEntity<?> uploadAllPendingPOIs(@RequestParam UUID idUser) {
        if (_userService.appropriateUser(idUser, UserPermission.AUTHCONTRIBUTOR_CONTENT_UPLOAD)) {
            List<PendingPOIDto> result = _poiService.uploadAllPendingPOIs();
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
    public ResponseEntity<?> uploadPendingPOI(@PathVariable("id") UUID id, @RequestParam UUID idUser) {
        if (_userService.appropriateUser(idUser, UserPermission.AUTHCONTRIBUTOR_CONTENT_UPLOAD)) {
            PendingPOIDto poiDto = _poiService.uploadPendingPOI(id);
            if (poiDto != null) {
                return new ResponseEntity<>(poiDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @PatchMapping("/authorized/uploadable")
    public ResponseEntity<?> uploadAllAuthorizedPOIs(@RequestParam UUID idUser) {
        if (_userService.appropriateUser(idUser, UserPermission.AUTHCONTRIBUTOR_CONTENT_UPLOAD)) {
            List<AuthorizedPOIDto> result = _poiService.uploadAllAuthorizedPOIs();
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
    public ResponseEntity<?> uploadAuthorizedPOI(@PathVariable("id") UUID id, @RequestParam UUID idUser) {
        if (_userService.appropriateUser(idUser, UserPermission.AUTHCONTRIBUTOR_CONTENT_UPLOAD)) {
            AuthorizedPOIDto poiDto = _poiService.uploadAuthorizedPOI(id);
            if (poiDto != null) {
                return new ResponseEntity<>(poiDto, HttpStatus.OK);
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
                Optional<Predicate<POI>> predicate = Optional.of(poi -> poi.getId().equals(id) && poi.getState().equals(ContentState.UPLOADED));
                PendingAttachmentDto attachmentDto = new PendingAttachmentDto();
                attachmentDto.setState(ContentState.VALIDABLE);
                AttachmentDto result = _poiService.addPendingAttachment(modifyAttachmentConfiguration(attachmentDto, request), predicate);
                if (result != null) {
                    return new ResponseEntity<>(result, HttpStatus.OK);
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
                Optional<Predicate<POI>> predicate = Optional.of(poi -> poi.getId().equals(id) && poi.getState().equals(ContentState.UPLOADED));
                AuthorizedAttachmentDto attachmentDto = new AuthorizedAttachmentDto();
                AttachmentDto result = _poiService.addAuthorizedAttachment(modifyAttachmentConfiguration(attachmentDto, request), predicate);
                if (result != null) {
                    return new ResponseEntity<>(result, HttpStatus.OK);
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

    @GetMapping("/poiitineraries/{id}")
    public ResponseEntity<?> getPoiitineraries(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(GenericItineraryMapper.toDto(_poiService.getPOIById(id).getPoiItineraries(), true), HttpStatus.OK);
    }
}
