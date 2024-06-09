package it.unicam.cs.idsflsm.municipalplatform.application.mappers.attachment;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AuthorizedAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.PendingAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.AuthorizedAttachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.PendingAttachment;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility generic class for mapping between Attachment and AttachmentDto
 */
public class GenericAttachmentMapper {
    /**
     * Converts an Attachment entity to an Attachment DTO
     * @param attachment the attachment entity to be converted
     * @param includeRelativeEntities flag indicating whether to map the sub-entities
     * @return the corresponding AttachmentDto if attachment in the parameter is not null, null otherwise
     */
    public static AttachmentDto toDto(Attachment attachment, boolean includeRelativeEntities) {
        if (attachment != null) {
            if (attachment instanceof PendingAttachment pendingAttachment) {
                return PendingAttachmentMapper.toDto(pendingAttachment, includeRelativeEntities);
            } else if (attachment instanceof AuthorizedAttachment authorizedAttachment) {
                return AuthorizedAttachmentMapper.toDto(authorizedAttachment, includeRelativeEntities);
            }
        }
        return null;
    }
    /**
     * Converts an Attachment DTO to an Attachment entity
     * @param attachmentDto the attachment DTO to be converted
     * @param includeRelativeEntities flag indicating whether to map the sub-DTOs
     * @return the corresponding Attachment if the attachmentDto in the parameter is not null, null otherwise
     */
    public static Attachment toEntity(AttachmentDto attachmentDto, boolean includeRelativeEntities) {
        if (attachmentDto != null) {
            if (attachmentDto instanceof PendingAttachmentDto pendingAttachmentDto) {
                return PendingAttachmentMapper.toEntity(pendingAttachmentDto, includeRelativeEntities);
            } else if (attachmentDto instanceof AuthorizedAttachmentDto authorizedAttachmentDto) {
                return AuthorizedAttachmentMapper.toEntity(authorizedAttachmentDto, includeRelativeEntities);
            }
        }
        return null;
    }
    /**
     * Converts a list of Attachment entities to a list of Attachment DTOs
     * @param attachments the list of Attachment entities to be converted
     * @param includeRelativeEntities flag indicating whether to map the sub-entities
     * @return the corresponding list of AttachmentDto if the list in the parameter is not null, null otherwise
     */
    public static List<AttachmentDto> toDto(List<Attachment> attachments, boolean includeRelativeEntities) {
        if (attachments != null) {
            return attachments.stream()
                    .map(attachment -> GenericAttachmentMapper.toDto(attachment, includeRelativeEntities))
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }
    /**
     * Converts a list of Attachment DTOs to a list of Attachment entities
     * @param attachmentDtos the list of Attachment DTOs to be converted
     * @param includeRelativeEntities flag indicating whether to map the sub-DTOs
     * @return the corresponding list of Attachment if the list in the parameter is not null, null otherwise
     */
    public static List<Attachment> toEntity(List<AttachmentDto> attachmentDtos, boolean includeRelativeEntities) {
        if (attachmentDtos != null) {
            return attachmentDtos.stream()
                    .map(attachmentDto -> GenericAttachmentMapper.toEntity(attachmentDto, includeRelativeEntities))
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }
}
