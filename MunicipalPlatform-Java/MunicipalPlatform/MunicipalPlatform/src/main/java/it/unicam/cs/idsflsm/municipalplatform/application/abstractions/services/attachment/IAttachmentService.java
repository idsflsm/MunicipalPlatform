package it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.attachment;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AuthorizedAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.PendingAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.report.ReportDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserPermission;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

/**
 * Interface for Attachment service class. It provides methods to manipulate persistent
 * attachments in the database
 */
public interface IAttachmentService {
    void saveInRepository(Attachment attachment);
    void deleteFromRepository(Attachment attachment);
    List<PendingAttachmentDto> getAllPendingAttachments(Optional<Predicate<Attachment>> predicate);
    List<AuthorizedAttachmentDto> getAllAuthorizedAttachments(Optional<Predicate<Attachment>> predicate);

    /**
     * Method that retrieves a list of Attachment DTOs based on a given predicate
     * @param predicate an Optional Predicate<Attachment> that can be used to filter the attachments
     * @return the list of Attachment DTOs. If the predicate is present, the list will only contain
     * attachments that satisfy the predicate. If no attachments satisfy the predicate or if the
     * repository contains no attachments, an empty list will be returned
     */
    List<AttachmentDto> getAttachments(Optional<Predicate<Attachment>> predicate);

    PendingAttachmentDto getPendingAttachmentById(UUID id);
    AuthorizedAttachmentDto getAuthorizedAttachmentById(UUID id);

    /**
     * Method that retrieves an Attachment DTO by its unique identifier
     * @param id the UUID of the attachment to be retrieved
     * @return an Attachment DTO if an attachment with the given UUID exists,
     * null otherwise
     */
    AttachmentDto getAttachmentById(UUID id);

//    PendingAttachmentDto addPendingAttachment(PendingAttachmentDto attachmentDto);
//    AuthorizedAttachmentDto addAuthorizedAttachment(AuthorizedAttachmentDto attachmentDto);
//    boolean deletePendingAttachmentById(UUID id);
//    boolean deleteAuthorizedAttachmentById(UUID id);

    /**
     * Method that deletes an Attachment entity by its unique identifier
     * and returns the deleted attachment
     * @param id the UUID of the attachment to be deleted
     * @return an Attachment DTO if an attachment with the given UUID exists and was deleted,
     * null otherwise
     */
    AttachmentDto deleteAttachmentById(UUID id);

    PendingAttachmentDto updatePendingAttachment(PendingAttachmentDto attachmentDto, Optional<Predicate<Attachment>> predicate);
    AuthorizedAttachmentDto updateAuthorizedAttachment(AuthorizedAttachmentDto attachmentDto, Optional<Predicate<Attachment>> predicate);

    /**
     * Method that updates an existing Attachment entity based on the provided Attachment DTO
     * and returns the updated attachment
     * @param attachmentDto the Attachment DTO containing the updated information of the attachment
     * @return an Attachment DTO that represents the updated attachment
     */
    AttachmentDto updateAttachment(AttachmentDto attachmentDto);

    PendingAttachmentDto validatePendingAttachment(Optional<Predicate<Attachment>> predicate, boolean validate);

    /**
     * Method that validates an Attachment entity based on a provided predicate and a validation flag
     * @param predicate the Predicate<Attachment> used to filter the attachments
     * @param validate boolean flag indicating whether the attachment should be validated or not
     * @return an Attachment DTO if an attachment satisfying the predicate exists,
     * null otherwise
     */
    AttachmentDto validateAttachment(Predicate<Attachment> predicate, boolean validate);

    List<PendingAttachmentDto> uploadAllPendingAttachments();
    PendingAttachmentDto uploadPendingAttachment(UUID id);
    List<AuthorizedAttachmentDto> uploadAllAuthorizedAttachments();
    AuthorizedAttachmentDto uploadAuthorizedAttachment(UUID id);

    /**
     * Method that uploads an Attachment entity by its unique identifier
     * and returns the uploaded attachment
     * @param id the UUID of the attachment to be uploaded
     * @return an Attachment DTO if an attachment with the given UUID exists and is in UPLOADABLE state,
     * null otherwise
     */
    AttachmentDto uploadAttachmentById(UUID id);

    /**
     * Method that adds a report to an Attachment entity based on the provided Report DTO
     * and a predicate for attachments
     * @param reportDto the Report DTO containing the information of the report to be added
     * @param predicate the Predicate<Attachment> used to filter the attachments
     * @return a Report DTO if an attachment satisfying the predicate exists and the report is successfully added,
     * null otherwise
     */
    ReportDto addReport(ReportDto reportDto, Predicate<Attachment> predicate);
}
