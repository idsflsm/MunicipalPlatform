package it.unicam.cs.idsflsm.municipalplatform.application.mappers.attachment;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.itinerary.GenericItineraryMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.poi.GenericPOIMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.report.ReportMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.PendingAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.PendingAttachment;

import java.util.List;
import java.util.stream.Collectors;
public class PendingAttachmentMapper {

    public static PendingAttachmentDto toDto(PendingAttachment attachment, boolean includeRelatedEntities) {
        if (attachment == null) {
            return null;
        }

        PendingAttachmentDto dto = new PendingAttachmentDto();
        dto.setId(attachment.getId());
        dto.setName(attachment.getName());
        dto.setDescription(attachment.getDescription());
        dto.setAuthor(attachment.getAuthor());
        dto.setCreationDate(attachment.getCreationDate());
        dto.setExpiryDate(attachment.getExpiryDate());
        dto.setState(attachment.getState());

        if (includeRelatedEntities) {
            dto.setPoi(GenericPOIMapper.toDto(attachment.getPoi(), false));
            dto.setItinerary(GenericItineraryMapper.toDto(attachment.getItinerary(), false));
            dto.setReports(ReportMapper.toDto(attachment.getReports(), false));
        }

        return dto;
    }

    public static PendingAttachment toEntity(PendingAttachmentDto dto, boolean includeRelatedEntities) {
        if (dto == null) {
            return null;
        }

        PendingAttachment entity = new PendingAttachment();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setAuthor(dto.getAuthor());
        entity.setCreationDate(dto.getCreationDate());
        entity.setExpiryDate(dto.getExpiryDate());
        entity.setState(dto.getState());

        if (includeRelatedEntities) {
            entity.setPoi(GenericPOIMapper.toEntity(dto.getPoi(), false));
            entity.setItinerary(GenericItineraryMapper.toEntity(dto.getItinerary(), false));
            entity.setReports(ReportMapper.toEntity(dto.getReports(), false));
        }

        return entity;
    }
}
