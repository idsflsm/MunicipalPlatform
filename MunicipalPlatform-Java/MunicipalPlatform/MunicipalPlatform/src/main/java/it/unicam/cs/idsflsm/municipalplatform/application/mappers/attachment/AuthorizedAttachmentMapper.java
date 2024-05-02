package it.unicam.cs.idsflsm.municipalplatform.application.mappers.attachment;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AuthorizedAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.PendingAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.AuthorizedAttachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.PendingAttachment;

import java.util.List;
import java.util.stream.Collectors;
public class AuthorizedAttachmentMapper {
    public static AuthorizedAttachmentDto toDto(AuthorizedAttachment attachment) {
        if (attachment != null) {
            return new AuthorizedAttachmentDto(
                    attachment.getId(),
                    attachment.getName(),
                    attachment.getDescription(),
                    attachment.getAuthor(),
                    attachment.getCreationDate(),
                    attachment.getExpiryDate(),
                    attachment.getState(),
                    attachment.getPoi(),
                    attachment.getItinerary(),
                    attachment.getReports()
            );
        } else {
            return null;
        }
    }
    public static AuthorizedAttachment toEntity(AuthorizedAttachmentDto attachmentDto) {
        if (attachmentDto != null) {
            return new AuthorizedAttachment(
                    attachmentDto.getId(),
                    attachmentDto.getName(),
                    attachmentDto.getDescription(),
                    attachmentDto.getAuthor(),
                    attachmentDto.getCreationDate(),
                    attachmentDto.getExpiryDate(),
                    attachmentDto.getState(),
                    attachmentDto.getPoi(),
                    attachmentDto.getItinerary(),
                    attachmentDto.getReports()
            );
        } else {
            return null;
        }
    }
    public static List<AuthorizedAttachmentDto> toDto(List<AuthorizedAttachment> attachments) {
        if (attachments != null) {
            return attachments.stream().map(AuthorizedAttachmentMapper::toDto).collect(Collectors.toList());
        } else {
            return null;
        }
    }
    public static List<AuthorizedAttachment> toEntity(List<AuthorizedAttachmentDto> attachmentDtos) {
        if (attachmentDtos != null) {
            return attachmentDtos.stream().map(AuthorizedAttachmentMapper::toEntity).collect(Collectors.toList());
        } else {
            return null;
        }
    }
}
