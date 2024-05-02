package it.unicam.cs.idsflsm.municipalplatform.application.mappers.attachment;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.PendingAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.PendingAttachment;

import java.util.List;
import java.util.stream.Collectors;
public class PendingAttachmentMapper {
    public static PendingAttachmentDto toDto(PendingAttachment attachment) {
        if (attachment != null) {
            return new PendingAttachmentDto(
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
    public static PendingAttachment toEntity(PendingAttachmentDto attachmentDto) {
        if (attachmentDto != null) {
            return new PendingAttachment(
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
    public static List<PendingAttachmentDto> toDto(List<PendingAttachment> attachments) {
        if (attachments != null) {
            return attachments.stream().map(PendingAttachmentMapper::toDto).collect(Collectors.toList());
        } else {
            return null;
        }
    }
    public static List<PendingAttachment> toEntity(List<PendingAttachmentDto> attachmentDtos) {
        if (attachmentDtos != null) {
            return attachmentDtos.stream().map(PendingAttachmentMapper::toEntity).collect(Collectors.toList());
        } else {
            return null;
        }
    }
}
