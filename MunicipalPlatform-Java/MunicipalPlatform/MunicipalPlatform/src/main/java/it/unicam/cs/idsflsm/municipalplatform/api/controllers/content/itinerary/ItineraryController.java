package it.unicam.cs.idsflsm.municipalplatform.api.controllers.content.itinerary;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidContentStateException;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidDateFormatException;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidLatitudeException;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidLongitudeException;
import it.unicam.cs.idsflsm.municipalplatform.api.helpers.attachment.AttachmentHelper;
import it.unicam.cs.idsflsm.municipalplatform.api.helpers.content.itinerary.ItineraryHelper;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.content.itinerary.IItineraryService;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.user.IUserService;
import it.unicam.cs.idsflsm.municipalplatform.application.criterias.content.itinerary.ItineraryCriteria;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AuthorizedAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.PendingAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.AuthorizedItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.ItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.PendingItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedUserDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.attachment.AddAttachmentRequest;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.content.itinerary.AddItineraryRequest;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.content.itinerary.UpdateItineraryRequest;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.content.itinerary.ValidatePendingItineraryRequest;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Coordinates;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserPermission;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
@RestController
@RequestMapping("/api/itineraries")
@AllArgsConstructor(onConstructor_ = @Autowired)
public class ItineraryController {
    /**
     * The service for Itinerary entity
     */
    private final IItineraryService _itineraryService;
    /**
     * The service for User entities
     */
    private final IUserService _userService;
    /**
     * The helper for Itinerary entity
     */
    private final ItineraryHelper _itineraryHelper;
    /**
     * The helper for Attachment entity
     */
    private final AttachmentHelper _attachmentHelper;
    /**
     * Method that retrieves a list of Itinerary DTOs after filtering
     *
     * @param idUser       the UUID of the user performing the operation
     * @param name         the name of desired itinerary
     * @param latitude     the latitude of desired itinerary
     * @param longitude    the longitude of desired itinerary
     * @param description  the description of desired itinerary
     * @param author       the author of desired itinerary
     * @param creationDate the creation date of desired itinerary
     * @param expiryDate   the expiry date of desired itinerary
     * @param state        the state of desired itinerary
     * @return the list of found Itinerary DTOs, based on params
     */
    @GetMapping
    public ResponseEntity<?> getItineraries
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
            String finalState = state;
            criterias = getItinerariesCriterias(name, latitude, longitude, description, author, creationDate, expiryDate, state);
            List<ItineraryDto> itineraryDtos = _itineraryService.getItineraries(Optional.of(criterias));
            itineraryDtos.forEach(itineraryDto -> {
                itineraryDto = itineraryDto.allWithState(ContentState.fromString(finalState));
            });
            if (!itineraryDtos.isEmpty()) {
                return new ResponseEntity<>(itineraryDtos, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (InvalidLatitudeException | InvalidLongitudeException | InvalidDateFormatException |
                 InvalidContentStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    private Predicate<Itinerary> getItinerariesCriterias(String name, String latitude, String longitude, String description, String author, String creationDate, String expiryDate, String state) {
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
    /**
     * Method that retrieves an Itinerary DTO by its unique identifier
     *
     * @param id     the UUID of desired itinerary
     * @param idUser the UUID of the user performing the operation
     * @return the Itinerary DTO if exists
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getItineraryById(@PathVariable("id") UUID id, @RequestParam UUID idUser) {
        boolean uploaded = true;
        ItineraryDto itineraryDto = _itineraryService.getItineraryById(id);
        if (_userService.appropriateUser(idUser, UserPermission.TOURIST_CONTENT_READ)) {
            uploaded = itineraryDto.getState().equals(ContentState.UPLOADED);
            itineraryDto = itineraryDto.allWithState(ContentState.UPLOADED);
        }
        if (itineraryDto != null && uploaded) {
            return new ResponseEntity<>(itineraryDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    /**
     * Method that adds an Itinerary entity to the platform
     *
     * @param request the request for adding a new itinerary
     * @return the Itinerary DTO if has been added
     */
    @PostMapping
    public ResponseEntity<?> addItinerary(@RequestBody AddItineraryRequest request) {
        try {
            UserPermission permission = null;
            ContentState state = null;
            ItineraryDto itineraryDto = null;
            if (_userService.appropriateUser(request.getIdUser(), UserPermission.CONTRIBUTOR_CONTENT_CREATE_PENDING)) {
                permission = UserPermission.CONTRIBUTOR_CONTENT_CREATE_PENDING;
                state = ContentState.VALIDABLE;
                itineraryDto = new PendingItineraryDto();
            } else if (_userService.appropriateUser(request.getIdUser(), UserPermission.AUTHCONTRIBUTOR_CONTENT_CREATE_AUTHORIZED)) {
                permission = UserPermission.AUTHCONTRIBUTOR_CONTENT_CREATE_AUTHORIZED;
                state = ContentState.UPLOADABLE;
                itineraryDto = new AuthorizedItineraryDto();
            }
            if (permission != null && state != null && itineraryDto != null) {
                ItineraryDto result = _itineraryService.addItinerary(_itineraryHelper.modifyItineraryConfiguration(itineraryDto, request, state), permission);
                return new ResponseEntity<>(result, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (InvalidLatitudeException | InvalidLongitudeException | InvalidDateFormatException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
//    TODO : FOR TESTING PURPOSES ONLY
    /**
     * Method that deletes an Itinerary entity by its unique identifier
     * and returns the deleted itinerary
     *
     * @param id the UUID of desired itinerary
     * @return the Itinerary DTO if has been deleted
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItineraryById(@PathVariable("id") UUID id) {
        ItineraryDto itineraryDto = _itineraryService.deleteItineraryById(id);
        if (itineraryDto != null) {
            return new ResponseEntity<>(itineraryDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    /**
     * Method that updates an existing Itinerary entity based on the provided Itinerary DTO
     * and returns the updated itinerary
     *
     * @param id      the UUID of desired itinerary
     * @param request the request for updating the desired itinerary
     * @return the Itinerary DTO if has been updated
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateItineraryById(@PathVariable("id") UUID id, @RequestBody UpdateItineraryRequest request) {
        try {
            if (_userService.appropriateUser(request.getIdUser(), UserPermission.CURATOR_CONTENT_UPDATE)) {
                Predicate<Itinerary> predicate = ItineraryCriteria.criteriaBuilder(
                        ItineraryCriteria.isInUploadedState(),
                        ItineraryCriteria.hasId(id)
                );
                List<ItineraryDto> itineraryDtos = _itineraryService.getItineraries(Optional.of(predicate));
                ItineraryDto itineraryDto = (!itineraryDtos.isEmpty()) ? itineraryDtos.get(0) : null;
                if (itineraryDto != null) {
                    ItineraryDto result = _itineraryService.updateItinerary(_itineraryHelper.modifyItineraryConfiguration(itineraryDto, request, ContentState.UPLOADABLE));
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
     * Method that validates an Itinerary entity based on a provided predicate and a validation flag
     *
     * @param id      the UUID of desired itinerary
     * @param request the request for validating the desired itinerary
     * @return the Itinerary DTO if has been validated
     */
    @PatchMapping("/{id}/validate")
    public ResponseEntity<?> validateItinerary(@PathVariable("id") UUID id, @RequestParam ValidatePendingItineraryRequest request) {
        if (_userService.appropriateUser(request.getIdUser(), UserPermission.CURATOR_CONTENT_VALIDATE)) {
            Predicate<Itinerary> predicate = ItineraryCriteria.criteriaBuilder(
                    ItineraryCriteria.isInValidableState(),
                    ItineraryCriteria.hasId(id)
            );
            ItineraryDto result = _itineraryService.validateItinerary(predicate, request.isValidate());
            if (result != null) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    /**
     * Method that saves an Itinerary entity to a user's favorites itineraries
     *
     * @param id     the UUID of desired itinerary
     * @param idUser the UUID of the user performing the operation
     * @return the Itinerary DTO if has been saved
     */
    @PatchMapping("/{id}/save")
    public ResponseEntity<?> saveItinerary(@PathVariable UUID id, @RequestParam UUID idUser) {
        if (_userService.appropriateUser(idUser, UserPermission.AUTHTOURIST_CONTENT_SAVE)) {
            AuthenticatedUserDto userDto = _userService.findById(idUser);
            ItineraryDto result = _itineraryService.saveItinerary(id, userDto);
            if (result != null) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    /**
     * Method that uploads an Itinerary entity by its unique identifier
     *
     * @param id     the UUID of desired itinerary
     * @param idUser the UUID of the user performing the operation
     * @return the Itinerary DTO if has been uploaded
     */
    @PatchMapping("/{id}/upload")
    public ResponseEntity<?> uploadItinerary(@PathVariable("id") UUID id, @RequestParam UUID idUser) {
        if (_userService.appropriateUser(idUser, UserPermission.AUTHCONTRIBUTOR_CONTENT_UPLOAD)) {
            ItineraryDto result = _itineraryService.uploadItineraryById(id);
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
                Predicate<Itinerary> predicate = ItineraryCriteria.criteriaBuilder(
                        ItineraryCriteria.isInUploadedState(),
                        ItineraryCriteria.hasId(id)
                );
                AttachmentDto result = _itineraryService.addAttachment(_attachmentHelper.modifyAttachmentConfiguration(attachmentDto, request), permission, predicate);
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
