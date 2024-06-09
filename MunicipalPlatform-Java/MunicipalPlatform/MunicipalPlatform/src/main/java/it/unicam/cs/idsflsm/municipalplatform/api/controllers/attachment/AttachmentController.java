package it.unicam.cs.idsflsm.municipalplatform.api.controllers.attachment;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidContentStateException;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidDateFormatException;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.attachment.IAttachmentService;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.user.IUserService;
import it.unicam.cs.idsflsm.municipalplatform.application.criterias.attachment.AttachmentCriteria;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AuthorizedAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.PendingAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.report.ReportDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.attachment.*;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.attachment.UpdateAttachmentRequest;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
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
@RequestMapping("/api/attachments")
@AllArgsConstructor(onConstructor_ = @Autowired)
public class AttachmentController {
    /**
     * The service for Attachment entity
     */
    private final IAttachmentService _attachmentService;
    /**
     * The service for User entities
     */
    private final IUserService _userService;
    @GetMapping("/pending")
    public ResponseEntity<?> getAllPendingAttachments
            (@RequestParam(required = true) UUID idUser,
             @RequestParam(required = false) String name,
             @RequestParam(required = false) String description,
             @RequestParam(required = false) String author,
             @RequestParam(required = false) String creationDate,
             @RequestParam(required = false) String expiryDate,
             @RequestParam(required = false) String state) {
        try {
            Predicate<Attachment> criterias;
            if (_userService.appropriateUser(idUser, UserPermission.TOURIST_CONTENT_READ)) {
                state = "UPLOADED";
            }
            String finalState = state;
            criterias = AttachmentCriteria.isPendingAttachment()
                    .and(getAttachmentsCriterias(name, description, author, creationDate, expiryDate, state));
            List<PendingAttachmentDto> pendingAttachmentDtos =
                    _attachmentService.getAllPendingAttachments(Optional.of(criterias));
            pendingAttachmentDtos.forEach(pendingAttachmentDto -> {
                pendingAttachmentDto = (PendingAttachmentDto) pendingAttachmentDto.allWithState(ContentState.fromString(finalState));
            });
            if (!pendingAttachmentDtos.isEmpty()) {
                return new ResponseEntity<>(pendingAttachmentDtos, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (InvalidDateFormatException | InvalidContentStateException e1) {
            return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/authorized")
    public ResponseEntity<?> getAllAuthorizedAttachments
            (@RequestParam(required = true) UUID idUser,
             @RequestParam(required = false) String name,
             @RequestParam(required = false) String description,
             @RequestParam(required = false) String author,
             @RequestParam(required = false) String creationDate,
             @RequestParam(required = false) String expiryDate,
             @RequestParam(required = false) String state) {
        try {
            Predicate<Attachment> criterias;
            if (_userService.appropriateUser(idUser, UserPermission.TOURIST_CONTENT_READ)) {
                state = "UPLOADED";
            }
            String finalState = state;
            criterias = AttachmentCriteria.isAuthorizedAttachment()
                    .and(getAttachmentsCriterias(name, description, author, creationDate, expiryDate, state));
            List<AuthorizedAttachmentDto> authorizedAttachmentDtos =
                    _attachmentService.getAllAuthorizedAttachments(Optional.of(criterias));
            authorizedAttachmentDtos.forEach(attachmentDto -> {
                attachmentDto = (AuthorizedAttachmentDto) attachmentDto.allWithState(ContentState.fromString(finalState));
            });
            if (!authorizedAttachmentDtos.isEmpty()) {
                return new ResponseEntity<>(authorizedAttachmentDtos, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (InvalidDateFormatException | InvalidContentStateException e1) {
            return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


//    TODO : MERGING OF PENDING AND AUTHORIZED

    /**
     * Method that retrieves a list of Attachment DTO after filtering
     * @param idUser the UUID of the user performing the operation
     * @param name the name of desired attachments
     * @param description the description of desired attachments
     * @param author the author of desired attachments
     * @param creationDate the creation date of desired attachments
     * @param expiryDate the expiry date of desired attachments
     * @param state the state of desired attachments
     * @return the list of found Attachment DTOs, based on params
     */
    @GetMapping
    public ResponseEntity<?> getAttachments
            (@RequestParam(required = true) UUID idUser,
             @RequestParam(required = false) String name,
             @RequestParam(required = false) String description,
             @RequestParam(required = false) String author,
             @RequestParam(required = false) String creationDate,
             @RequestParam(required = false) String expiryDate,
             @RequestParam(required = false) String state) {
        try {
            Predicate<Attachment> criterias;
            if (_userService.appropriateUser(idUser, UserPermission.TOURIST_CONTENT_READ)) {
                state = "UPLOADED";
            }
            String finalState = state;
            criterias = getAttachmentsCriterias(name, description, author, creationDate, expiryDate, state);
            List<AttachmentDto> attachmentDtos = _attachmentService.getAttachments(Optional.of(criterias));
            attachmentDtos.forEach(attachmentDto -> {
                attachmentDto = attachmentDto.allWithState(ContentState.fromString(finalState));
            });
            if (!attachmentDtos.isEmpty()) {
                return new ResponseEntity<>(attachmentDtos, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (InvalidDateFormatException | InvalidContentStateException e1) {
            return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    private Predicate<Attachment> getAttachmentsCriterias(String name, String description, String author, String creationDate, String expiryDate, String state) {
        Date cd = (creationDate != null) ? Date.fromString(creationDate) : null;
        Date ed = (expiryDate != null) ? Date.fromString(expiryDate) : null;
        ContentState cs = (state != null) ? ContentState.fromString(state) : null;
        return AttachmentCriteria.criteriaBuilder(
                AttachmentCriteria.hasName(name),
                AttachmentCriteria.hasDescription(description),
                AttachmentCriteria.hasAuthor(author),
                AttachmentCriteria.hasCreationDate(cd),
                AttachmentCriteria.hasExpiryDate(ed),
                AttachmentCriteria.hasState(cs)
        );
    }
    @GetMapping("/pending/{id}")
    public ResponseEntity<?> getPendingAttachment(@PathVariable("id") UUID id, @RequestParam UUID idUser) {
        boolean uploaded = true;
        PendingAttachmentDto pendingAttachmentDto = _attachmentService.getPendingAttachmentById(id);
        if (_userService.appropriateUser(idUser, UserPermission.TOURIST_CONTENT_READ)) {
            uploaded = pendingAttachmentDto.getState().equals(ContentState.UPLOADED);
            pendingAttachmentDto = (PendingAttachmentDto) pendingAttachmentDto.allWithState(ContentState.UPLOADED);
        }
        if (pendingAttachmentDto != null && uploaded) {
            return new ResponseEntity<>(pendingAttachmentDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
    @GetMapping("/authorized/{id}")
    public ResponseEntity<?> getAuthorizedAttachment(@PathVariable("id") UUID id, @RequestParam UUID idUser) {
        boolean uploaded = true;
        AuthorizedAttachmentDto authorizedAttachmentDto = _attachmentService.getAuthorizedAttachmentById(id);
        if (_userService.appropriateUser(idUser, UserPermission.TOURIST_CONTENT_READ)) {
            uploaded = authorizedAttachmentDto.getState().equals(ContentState.UPLOADED);
            authorizedAttachmentDto = (AuthorizedAttachmentDto) authorizedAttachmentDto.allWithState(ContentState.UPLOADED);
        }
        if (authorizedAttachmentDto != null && uploaded) {
            return new ResponseEntity<>(authorizedAttachmentDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


    //    TODO : MERGING OF PENDING AND AUTHORIZED

    /**
     * Method that retrieves an Attachment DTO by its unique identifier
     * @param id the UUID of desired attachment
     * @param idUser the UUID of the user performing the operation
     * @return the Attachment DTO if exists
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getAttachmentById(@PathVariable("id") UUID id, @RequestParam UUID idUser) {
        boolean uploaded = true;
        AttachmentDto attachmentDto = _attachmentService.getAttachmentById(id);
        if (_userService.appropriateUser(idUser, UserPermission.TOURIST_CONTENT_READ)) {
            uploaded = attachmentDto.getState().equals(ContentState.UPLOADED);
            attachmentDto = attachmentDto.allWithState(ContentState.UPLOADED);
        }
        if (attachmentDto != null && uploaded) {
            return new ResponseEntity<>(attachmentDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


//    @DeleteMapping("/pending/{id}")
//    public ResponseEntity<?> deletePendingAttachment(@PathVariable("id") UUID id) {
//        boolean result = _attachmentService.deletePendingAttachmentById(id);
//        if (result) {
//            return new ResponseEntity<>(HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//    @DeleteMapping("/authorized/{id}")
//    public ResponseEntity<?> deleteAuthorizedAttachment(@PathVariable("id") UUID id) {
//        boolean result = _attachmentService.deleteAuthorizedAttachmentById(id);
//        if (result) {
//            return new ResponseEntity<>(HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }


//    TODO : MERGING OF PENDING AND AUTHORIZED
//    TODO : FOR TESTING PURPOSES ONLY

    /**
     * Method that deletes an Attachment entity by its unique identifier
     * and returns the deleted attachment
     * @param id the UUID of desired attachment
     * @return the Attachment DTO if has been deleted
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAttachmentById(@PathVariable("id") UUID id) {
        AttachmentDto attachmentDto = _attachmentService.deleteAttachmentById(id);
        if (attachmentDto != null) {
            return new ResponseEntity<>(attachmentDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PutMapping("/pending/{id}")
    public ResponseEntity<?> updatePendingAttachment(@PathVariable("id") UUID id, @RequestBody UpdateAttachmentRequest request) {
        try {
            if (_userService.appropriateUser(request.getIdUser(), UserPermission.CURATOR_ATTACHMENT_UPDATE)) {
                Predicate<Attachment> predicate = AttachmentCriteria.isInUploadedState().and(attachment -> attachment.getId().equals(id));
                PendingAttachmentDto existingPendingAttachment = _attachmentService.getAllPendingAttachments(Optional.of(predicate)).get(0);
                if (existingPendingAttachment != null) {
                    PendingAttachmentDto result = _attachmentService.updatePendingAttachment(modifyAttachment(existingPendingAttachment, request), Optional.empty());
                    if (result != null) {
                        return new ResponseEntity<>(result, HttpStatus.OK);
                    } else {
                        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                    }
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        } catch (InvalidDateFormatException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/authorized/{id}")
    public ResponseEntity<?> updateAuthorizedAttachment(@PathVariable("id") UUID id, @RequestBody UpdateAttachmentRequest request) {
        try {
            if (_userService.appropriateUser(request.getIdUser(), UserPermission.CURATOR_ATTACHMENT_UPDATE)) {
                Predicate<Attachment> predicate = AttachmentCriteria.isInUploadedState().and(attachment -> attachment.getId().equals(id));
                AuthorizedAttachmentDto existingAuthorizedAttachment = _attachmentService.getAllAuthorizedAttachments(Optional.of(predicate)).get(0);
                if (existingAuthorizedAttachment != null) {
                    AuthorizedAttachmentDto result = _attachmentService.updateAuthorizedAttachment(modifyAttachment(existingAuthorizedAttachment, request), Optional.empty());
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
        } catch (InvalidDateFormatException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


//    TODO : MERGING OF PENDING AND AUTHORIZED

    /**
     * Method that updates an existing Attachment entity based on the provided Attachment DTO
     * and returns the updated attachment
     * @param id the UUID of desired attachment
     * @param request the request for updating the desired attachment
     * @return the Attachment DTO if has been updated
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAttachmentById(@PathVariable("id") UUID id, @RequestBody UpdateAttachmentRequest request) {
        try {
            if (_userService.appropriateUser(request.getIdUser(), UserPermission.CURATOR_ATTACHMENT_UPDATE)) {
                Predicate<Attachment> predicate = AttachmentCriteria.criteriaBuilder(
                        AttachmentCriteria.isInUploadedState(),
                        AttachmentCriteria.hasId(id)
                );
                List<AttachmentDto> attachmentDtos = _attachmentService.getAttachments(Optional.of(predicate));
                AttachmentDto attachmentDto = (!attachmentDtos.isEmpty()) ? attachmentDtos.get(0) : null;
                if (attachmentDto != null) {
                    AttachmentDto result = _attachmentService.updateAttachment(modifyAttachment(attachmentDto, request));
                    if (result != null) {
                        return new ResponseEntity<>(result, HttpStatus.OK);
                    }
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (InvalidDateFormatException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    private <T extends AttachmentDto, S extends ModifyAttachmentRequest> T modifyAttachment(T attachmentDto, S request) {
        attachmentDto.setName(request.getName());
        attachmentDto.setDescription(request.getDescription());
        attachmentDto.setAuthor(request.getAuthor());
        attachmentDto.setExpiryDate(Date.fromString(request.getExpiryDate()));
        attachmentDto.setState(ContentState.UPLOADABLE);
        return attachmentDto;
    }
    @PutMapping("/pending/validable/{id}")
    public ResponseEntity<?> validatePendingAttachment(@PathVariable("id") UUID id, @RequestBody ValidatePendingAttachmentRequest request) {
        if (_userService.appropriateUser(request.getIdUser(), UserPermission.CURATOR_ATTACHMENT_VALIDATE)) {
            Predicate<Attachment> predicate = AttachmentCriteria.hasId(id);
            PendingAttachmentDto result = _attachmentService.validatePendingAttachment(Optional.of(predicate), request.isValidate());
            if (result != null) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }


//    TODO : MERGING OF PENDING AND AUTHORIZED

    /**
     * Method that validates an Attachment entity based on a provided predicate and a validation flag
     * @param id the UUID of desired attachment
     * @param request the request for validating the desired attachment
     * @return the Attachment DTO if has been validated
     */
    @PatchMapping("/{id}/validate")
    public ResponseEntity<?> validateAttachment(@PathVariable("id") UUID id, @RequestBody ValidatePendingAttachmentRequest request) {
        if (_userService.appropriateUser(request.getIdUser(), UserPermission.CURATOR_ATTACHMENT_VALIDATE)) {
            Predicate<Attachment> predicate = AttachmentCriteria.criteriaBuilder(
                    AttachmentCriteria.isInValidableState(),
                    AttachmentCriteria.hasId(id)
            );
            AttachmentDto result = _attachmentService.validateAttachment(predicate, request.isValidate());
            if (result != null) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }


    @PatchMapping("/pending/uploadable")
    public ResponseEntity<?> uploadAllPendingAttachments(@RequestParam UUID idUser) {
        if (_userService.appropriateUser(idUser, UserPermission.AUTHCONTRIBUTOR_ATTACHMENT_UPLOAD)) {
            List<PendingAttachmentDto> result = _attachmentService.uploadAllPendingAttachments();
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
    public ResponseEntity<?> uploadPendingAttachment(@PathVariable("id") UUID id, @RequestParam UUID idUser) {
        if (_userService.appropriateUser(idUser, UserPermission.AUTHCONTRIBUTOR_ATTACHMENT_UPLOAD)) {
            PendingAttachmentDto attachmentDto = _attachmentService.uploadPendingAttachment(id);
            if (attachmentDto != null) {
                return new ResponseEntity<>(attachmentDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @PatchMapping("/authorized/uploadable")
    public ResponseEntity<?> uploadAllAuthorizedAttachments(@RequestParam UUID idUser) {
        if (_userService.appropriateUser(idUser, UserPermission.AUTHCONTRIBUTOR_ATTACHMENT_UPLOAD)) {
            List<AuthorizedAttachmentDto> result = _attachmentService.uploadAllAuthorizedAttachments();
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
    public ResponseEntity<?> uploadAuthorizedAttachment(@PathVariable("id") UUID id, @RequestParam UUID idUser) {
        if (_userService.appropriateUser(idUser, UserPermission.AUTHCONTRIBUTOR_ATTACHMENT_UPLOAD)) {
            AuthorizedAttachmentDto attachmentDto = _attachmentService.uploadAuthorizedAttachment(id);
            if (attachmentDto != null) {
                return new ResponseEntity<>(attachmentDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }


//    TODO : MERGING OF PENDING AND AUTHORIZED

    /**
     * Method that uploads an Attachment entity by its unique identifier
     * @param id the UUID of desired attachment
     * @param idUser the UUID of the user performing the operation
     * @return the Attachment DTO if has been uploaded
     */
    @PatchMapping("/{id}/upload")
    public ResponseEntity<?> uploadAttachment(@PathVariable("id") UUID id, @RequestParam UUID idUser) {
        if (_userService.appropriateUser(idUser, UserPermission.AUTHCONTRIBUTOR_ATTACHMENT_UPLOAD)) {
            AttachmentDto result = _attachmentService.uploadAttachmentById(id);
            if (result != null) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    /**
     * Method that adds a report to an Attachment entity based on the provided Report DTO
     * @param id the UUID of desired attachment
     * @param request the request for adding a report to the desired attachment
     * @return the Report DTO if has been added to desired attachment
     */
    @PostMapping("/{id}/reports")
    public ResponseEntity<?> addReport(@PathVariable("id") UUID id, @RequestBody AddReportRequest request) {
        if (_userService.appropriateUser(request.getIdUser(), UserPermission.TOURIST_REPORT_CREATE)) {
            ReportDto reportDto = new ReportDto();
            reportDto.setMotivation(request.getMotivation());
            Predicate<Attachment> predicate = AttachmentCriteria.criteriaBuilder(
                    AttachmentCriteria.isInUploadedState(),
                    AttachmentCriteria.hasId(id)
            );
            ReportDto result = _attachmentService.addReport(reportDto, predicate);
            if (result != null) {
                return new ResponseEntity<>(result, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
