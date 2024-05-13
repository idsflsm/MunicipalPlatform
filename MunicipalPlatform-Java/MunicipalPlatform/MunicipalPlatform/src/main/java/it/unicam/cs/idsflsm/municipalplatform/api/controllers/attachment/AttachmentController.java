package it.unicam.cs.idsflsm.municipalplatform.api.controllers.attachment;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidContentStateException;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidDateFormatException;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.attachment.IAttachmentService;
import it.unicam.cs.idsflsm.municipalplatform.application.criterias.attachment.AttachmentCriteria;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AuthorizedAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.PendingAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.report.ReportDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.attachment.AddReportRequest;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.attachment.ModifyAttachmentRequest;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.attachment.UpdateAttachmentRequest;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.attachment.UpdateAttachmentRequest;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;
import jakarta.validation.Valid;
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
    private final IAttachmentService _attachmentService;
    @GetMapping("/pending")
    public ResponseEntity<?> getAllPendingAttachments
            (@RequestParam(required = false) String name,
             @RequestParam(required = false) String description,
             @RequestParam(required = false) String author,
             @RequestParam(required = false) String creationDate,
             @RequestParam(required = false) String expiryDate,
             @RequestParam(required = false) String state) {
        try {
            Predicate<Attachment> criterias = AttachmentCriteria.isPendingAttachment()
                    .and(getAllCriterias(name, description, author, creationDate, expiryDate, state));
            List<PendingAttachmentDto> pendingAttachmentDtos =
                    _attachmentService.getAllPendingAttachments(Optional.of(criterias));
            return new ResponseEntity<>(pendingAttachmentDtos, HttpStatus.OK);
        } catch (InvalidDateFormatException | InvalidContentStateException e1) {
            return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/authorized")
    public ResponseEntity<?> getAllAuthorizedAttachments
            (@RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String creationDate,
            @RequestParam(required = false) String expiryDate,
            @RequestParam(required = false) String state) {
        try {
            Predicate<Attachment> criterias = AttachmentCriteria.isAuthorizedAttachment()
                    .and(getAllCriterias(name, description, author, creationDate, expiryDate, state));
            List<AuthorizedAttachmentDto> authorizedAttachmentDtos = // Change here
                    _attachmentService.getAllAuthorizedAttachments(Optional.of(criterias)); // Change here
            return new ResponseEntity<>(authorizedAttachmentDtos, HttpStatus.OK);
        } catch (InvalidDateFormatException | InvalidContentStateException e1) {
            return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    private Predicate<Attachment> getAllCriterias(String name, String description, String author, String creationDate, String expiryDate, String state) {
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
    public ResponseEntity<?> getPendingAttachment(@PathVariable("id") UUID id) {
        PendingAttachmentDto pendingAttachmentDto = _attachmentService.getPendingAttachmentById(id);
        if (pendingAttachmentDto != null) {
            return new ResponseEntity<>(pendingAttachmentDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/authorized/{id}")
    public ResponseEntity<?> getAuthorizedAttachment(@PathVariable("id") UUID id) {
        AuthorizedAttachmentDto authorizedAttachmentDto = _attachmentService.getAuthorizedAttachmentById(id);
        if (authorizedAttachmentDto != null) {
            return new ResponseEntity<>(authorizedAttachmentDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/pending/{id}")
    public ResponseEntity<?> deletePendingAttachment(@PathVariable("id") UUID id) {
        boolean result = _attachmentService.deletePendingAttachmentById(id);
        if (result) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/authorized/{id}")
    public ResponseEntity<?> deleteAuthorizedAttachment(@PathVariable("id") UUID id) {
        boolean result = _attachmentService.deleteAuthorizedAttachmentById(id);
        if (result) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/pending/{id}")
    public ResponseEntity<?> updatePendingAttachment(@PathVariable("id") UUID id, @Valid @RequestBody UpdateAttachmentRequest request) {
        try {
            PendingAttachmentDto existingPendingAttachment = _attachmentService.getPendingAttachmentById(id);
            if (existingPendingAttachment != null) {
                boolean result = _attachmentService.updatePendingAttachment(modifyAttachmentConfiguration(existingPendingAttachment, request, id), Optional.empty());
                if (result) {
                    return new ResponseEntity<>(existingPendingAttachment, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (InvalidDateFormatException e1) {
            return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/authorized/{id}")
    public ResponseEntity<?> updateAuthorizedAttachment(@PathVariable("id") UUID id, @Valid @RequestBody UpdateAttachmentRequest request) {
        try {
            AuthorizedAttachmentDto existingAuthorizedAttachment = _attachmentService.getAuthorizedAttachmentById(id);
            if (existingAuthorizedAttachment != null) {
                boolean result = _attachmentService.updateAuthorizedAttachment(modifyAttachmentConfiguration(existingAuthorizedAttachment, request, id), Optional.empty());
                if (result) {
                    return new ResponseEntity<>(existingAuthorizedAttachment, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
    @PutMapping("/pending/validable/{id}")
    public ResponseEntity<?> validatePendingAttachment(@PathVariable("id") UUID id, @RequestParam boolean validate) {
        PendingAttachmentDto attachmentDto = _attachmentService.getPendingAttachmentById(id);
        if (attachmentDto != null) {
            boolean result = _attachmentService.validatePendingAttachment(attachmentDto, Optional.empty(), validate);
            if (result) {
                return new ResponseEntity<>(attachmentDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/reports/{idAttachment}")
    public ResponseEntity<?> addReport(@PathVariable("idAttachment") UUID idAttachment, @Valid @RequestBody AddReportRequest request) {
        ReportDto reportDto = new ReportDto();
        reportDto.setMotivation(request.getMotivation());
        Optional<Predicate<Attachment>> predicate = Optional.of(AttachmentCriteria.hasId(idAttachment));
        boolean result = _attachmentService.addReport(reportDto, predicate);
        if (result) {
            return new ResponseEntity<>(reportDto, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
