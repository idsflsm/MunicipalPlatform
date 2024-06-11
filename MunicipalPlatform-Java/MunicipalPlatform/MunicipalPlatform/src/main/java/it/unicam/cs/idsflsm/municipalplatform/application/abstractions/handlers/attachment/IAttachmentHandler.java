package it.unicam.cs.idsflsm.municipalplatform.application.abstractions.handlers.attachment;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserPermission;
public interface IAttachmentHandler {
    /**
     * Method for modifying an Attachment entity, based on the provided
     * Attachment DTO and user permission
     *
     * @param attachmentDto the Attachment DTO containing new information
     * @param permission    the user permission determining the correct builder instance
     * @return the modified attachment entity
     */
    Attachment modifyAttachment(AttachmentDto attachmentDto, UserPermission permission);
}
