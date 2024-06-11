package it.unicam.cs.idsflsm.municipalplatform.api.controllers.content.poi;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidContentStateException;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidDateFormatException;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidLatitudeException;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidLongitudeException;
import it.unicam.cs.idsflsm.municipalplatform.api.helpers.attachment.AttachmentHelper;
import it.unicam.cs.idsflsm.municipalplatform.api.helpers.content.poi.POIHelper;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.content.poi.IPOIService;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.user.IUserService;
import it.unicam.cs.idsflsm.municipalplatform.application.criterias.content.poi.POICriteria;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AuthorizedAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.PendingAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.AuthorizedPOIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.POIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.PendingPOIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedUserDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.attachment.AddAttachmentRequest;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.content.poi.AddPOIRequest;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.content.poi.UpdatePOIRequest;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.content.poi.ValidatePendingPOIRequest;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
@RestController
@Validated
@RequestMapping("/api/pois")
@AllArgsConstructor(onConstructor_ = @Autowired)
public class POIController {
    /**
     * The service for POI entity
     */
    private final IPOIService _poiService;
    /**
     * The service for User entities
     */
    private final IUserService _userService;
    /**
     * The helper for POI entity
     */
    private final POIHelper _poiHelper;
    /**
     * The helper for Attachment entity
     */
    private final AttachmentHelper _attachmentHelper;
    /**
     * Method that retrieves a list of POI DTOs after filtering
     *
     * @param idUser       the UUID of the user performing the operation
     * @param name         the name of desired POI
     * @param latitude     the latitude of desired POI
     * @param longitude    the longitude of desired POI
     * @param description  the description of desired POI
     * @param author       the author of desired POI
     * @param creationDate the creation date of desired POI
     * @param expiryDate   the expiry date of desired POI
     * @param state        the state of desired POI
     * @return the list of found POI DTOs, based on params
     */
    @GetMapping
    public ResponseEntity<?> getPOIs
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
            criterias = getPOIsCriterias(name, latitude, longitude, description, author, creationDate, expiryDate, state);
            List<POIDto> poiDtos = _poiService.getPOIs(Optional.of(criterias));
            poiDtos.forEach(poiDto -> {
                poiDto = poiDto.allWithState(ContentState.fromString(finalState));
            });
            if (!poiDtos.isEmpty()) {
                return new ResponseEntity<>(poiDtos, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (InvalidLatitudeException | InvalidLongitudeException | InvalidDateFormatException |
                 InvalidContentStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    private Predicate<POI> getPOIsCriterias(String name, String latitude, String longitude, String description, String author, String creationDate, String expiryDate, String state) {
        Coordinates c = (latitude != null && longitude != null) ? Coordinates.fromStrings(latitude, longitude) : null;
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
    /**
     * Method that retrieves a POI DTO by its unique identifier
     *
     * @param id     the UUID of desired POI
     * @param idUser the UUID of the user performing the operation
     * @return the POI DTO if exists
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getPOIById(@PathVariable("id") UUID id, @RequestParam UUID idUser) {
        boolean uploaded = true;
        POIDto poiDto = _poiService.getPOIById(id);
        if (_userService.appropriateUser(idUser, UserPermission.TOURIST_CONTENT_READ)) {
            uploaded = poiDto.getState().equals(ContentState.UPLOADED);
            poiDto = poiDto.allWithState(ContentState.UPLOADED);
        }
        if (poiDto != null && uploaded) {
            return new ResponseEntity<>(poiDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    /**
     * Method that adds a POI entity to the platform
     *
     * @param request the request for adding a new POI
     * @return the POI DTO if has been added
     */
    @PostMapping
    public ResponseEntity<?> addPOI(@RequestBody AddPOIRequest request) {
        try {
            UserPermission permission = null;
            ContentState state = null;
            POIDto poiDto = null;
            if (_userService.appropriateUser(request.getIdUser(), UserPermission.CONTRIBUTOR_CONTENT_CREATE_PENDING)) {
                permission = UserPermission.CONTRIBUTOR_CONTENT_CREATE_PENDING;
                state = ContentState.VALIDABLE;
                poiDto = new PendingPOIDto();
            } else if (_userService.appropriateUser(request.getIdUser(), UserPermission.AUTHCONTRIBUTOR_CONTENT_CREATE_AUTHORIZED)) {
                permission = UserPermission.AUTHCONTRIBUTOR_CONTENT_CREATE_AUTHORIZED;
                state = ContentState.UPLOADABLE;
                poiDto = new AuthorizedPOIDto();
            }
            if (permission != null && state != null && poiDto != null) {
                POIDto result = _poiService.addPOI(_poiHelper.modifyPoiConfiguration(poiDto, request, state), permission);
                return new ResponseEntity<>(result, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (InvalidLatitudeException | InvalidLongitudeException | InvalidDateFormatException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
//    TODO : FOR TESTING PURPOSES ONLY
    /**
     * Method that deletes a POI entity by its unique identifier
     * and returns the deleted POI
     *
     * @param id the UUID of desired POI
     * @return the POI DTO if has been deleted
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePOI(@PathVariable("id") UUID id) {
        POIDto poiDto = _poiService.deletePOIById(id);
        if (poiDto != null) {
            return new ResponseEntity<>(poiDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    /**
     * Method that updates an existing POI entity based on the provided POI DTO
     * and returns the updated POI
     *
     * @param id      the UUID of desired POI
     * @param request the request for updating the desired POI
     * @return the POI DTO if has been updated
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePOI(@PathVariable("id") UUID id, @RequestBody UpdatePOIRequest request) {
        try {
            if (_userService.appropriateUser(request.getIdUser(), UserPermission.CURATOR_CONTENT_UPDATE)) {
                Predicate<POI> predicate = POICriteria.criteriaBuilder(
                        POICriteria.isInUploadedState(),
                        POICriteria.hasId(id)
                );
                List<POIDto> poiDtos = _poiService.getPOIs(Optional.of(predicate));
                POIDto poiDto = (!poiDtos.isEmpty()) ? poiDtos.get(0) : null;
                if (poiDto != null) {
                    POIDto result = _poiService.updatePOI(_poiHelper.modifyPoiConfiguration(poiDto, request, ContentState.UPLOADABLE));
                    if (result != null) {
                        return new ResponseEntity<>(result, HttpStatus.OK);
                    }
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (InvalidLatitudeException | InvalidLongitudeException | InvalidDateFormatException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    /**
     * Method that validates a POI entity based on a provided predicate and a validation flag
     *
     * @param id      the UUID of desired POI
     * @param request the request for validating the desired POI
     * @return the POI DTO if has been validated
     */
    @PatchMapping("/{id}/validate")
    public ResponseEntity<?> validatePOI(@PathVariable("id") UUID id, @RequestParam ValidatePendingPOIRequest request) {
        if (_userService.appropriateUser(request.getIdUser(), UserPermission.CURATOR_CONTENT_VALIDATE)) {
            Predicate<POI> predicate = POICriteria.criteriaBuilder(
                    POICriteria.isInValidableState(),
                    POICriteria.hasId(id)
            );
            POIDto result = _poiService.validatePOI(predicate, request.isValidate());
            if (result != null) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    /**
     * Method that saves a POI entity to a user's favorites POIs
     *
     * @param id     the UUID of desired POI
     * @param idUser the UUID of the user performing the operation
     * @return the POI DTO if has been saved
     */
    @PatchMapping("/{id}/save")
    public ResponseEntity<?> savePOI(@PathVariable UUID id, @RequestParam UUID idUser) {
        if (_userService.appropriateUser(idUser, UserPermission.AUTHTOURIST_CONTENT_SAVE)) {
            AuthenticatedUserDto userDto = _userService.findById(idUser);
            POIDto result = _poiService.savePOI(id, userDto);
            if (result != null) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    /**
     * Method that uploads a POI entity by its unique identifier
     *
     * @param id     the UUID of desired POI
     * @param idUser the UUID of the user performing the operation
     * @return the POI DTO if has been uploaded
     */
    @PatchMapping("/{id}/upload")
    public ResponseEntity<?> uploadPOI(@PathVariable("id") UUID id, @RequestParam UUID idUser) {
        if (_userService.appropriateUser(idUser, UserPermission.AUTHCONTRIBUTOR_CONTENT_UPLOAD)) {
            POIDto result = _poiService.uploadPOIById(id);
            if (result != null) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    /**
     * Method that adds an Attachment entity, for a particular itinerary, to the platform
     *
     * @param id      the UUID of desired itinerary
     * @param request the request for adding a new attachment to desired itinerary
     * @return the Attachment DTO if has been added
     */
    @PostMapping("/{id}/attachments")
    public ResponseEntity<?> addAttachment(@PathVariable("id") UUID id, @RequestBody AddAttachmentRequest request) {
        try {
            UserPermission permission = null;
            AttachmentDto attachmentDto = null;
            if (_userService.appropriateUser(request.getIdUser(), UserPermission.AUTHTOURIST_ATTACHMENT_CREATE)) {
                permission = UserPermission.AUTHTOURIST_ATTACHMENT_CREATE;
                attachmentDto = new PendingAttachmentDto();
            } else if (_userService.appropriateUser(request.getIdUser(), UserPermission.AUTHCONTRIBUTOR_ATTACHMENT_CREATE_AUTHORIZED)) {
                permission = UserPermission.AUTHCONTRIBUTOR_ATTACHMENT_CREATE_AUTHORIZED;
                attachmentDto = new AuthorizedAttachmentDto();
            }
            if (permission != null && attachmentDto != null) {
                Predicate<POI> predicate = POICriteria.criteriaBuilder(
                        POICriteria.isInUploadedState(),
                        POICriteria.hasId(id)
                );
                AttachmentDto result = _poiService.addAttachment(_attachmentHelper.modifyAttachmentConfiguration(attachmentDto, request), permission, predicate);
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
}
