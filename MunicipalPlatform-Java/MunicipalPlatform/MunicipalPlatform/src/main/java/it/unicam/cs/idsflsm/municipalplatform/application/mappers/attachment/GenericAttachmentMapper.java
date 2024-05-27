package it.unicam.cs.idsflsm.municipalplatform.application.mappers.attachment;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AuthorizedAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.PendingAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.AuthorizedAttachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.PendingAttachment;

import java.util.List;
import java.util.stream.Collectors;
public class GenericAttachmentMapper {
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
    public static List<AttachmentDto> toDto(List<Attachment> attachments, boolean includeRelativeEntities) {
        if (attachments != null) {
            return attachments.stream()
                    .map(attachment -> GenericAttachmentMapper.toDto(attachment, includeRelativeEntities))
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }
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
