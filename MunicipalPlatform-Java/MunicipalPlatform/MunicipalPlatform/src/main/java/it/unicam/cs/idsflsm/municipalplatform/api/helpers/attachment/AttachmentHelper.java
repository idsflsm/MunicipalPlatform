package it.unicam.cs.idsflsm.municipalplatform.api.helpers.attachment;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.attachment.ModifyAttachmentRequest;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
/**
 * Component class providing helper methods for modifying Attachment fields
 */
@Component
public class AttachmentHelper {
    /**
     * Method for modifying the Attachment DTO configuration, based on the provided request
     *
     * @param <T>           type of AttachmentDto
     * @param <S>           type of ModifyAttachmentRequest
     * @param attachmentDto the AttachmentDto instance to be modified
     * @param request       the ModifyAttachmentRequest instance containing the new configuration
     * @return the modified Attachment DTO
     */
    public <T extends AttachmentDto, S extends ModifyAttachmentRequest> T modifyAttachmentConfiguration
    (T attachmentDto, S request) {
        attachmentDto.setName(request.getName());
        attachmentDto.setDescription(request.getDescription());
        attachmentDto.setAuthor(request.getAuthor());
        attachmentDto.setCreationDate(Date.toDate(LocalDate.now()));
        attachmentDto.setExpiryDate(Date.fromString(request.getExpiryDate()));
        return attachmentDto;
    }
}
