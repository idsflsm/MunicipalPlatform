package it.unicam.cs.idsflsm.municipalplatform.application.mappers.attachment;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.itinerary.GenericItineraryMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.poi.GenericPOIMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.report.ReportMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.PendingAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.PendingAttachment;
/**
 * Utility class for mapping between PendingAttachment and PendingAttachmentDto
 */
public class PendingAttachmentMapper {
    /**
     * Converts a PendingAttachment entity to a PendingAttachment DTO
     *
     * @param attachment              the attachment entity to be converted
     * @param includeRelativeEntities flag indicating whether to map the sub-entities
     * @return the corresponding PendingAttachmentDto if the attachment parameter is not null, null otherwise
     */
    public static PendingAttachmentDto toDto(PendingAttachment attachment, boolean includeRelativeEntities) {
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
        if (includeRelativeEntities) {
            dto.setPoi(GenericPOIMapper.toDto(attachment.getPoi(), false));
            dto.setItinerary(GenericItineraryMapper.toDto(attachment.getItinerary(), false));
            dto.setReports(ReportMapper.toDto(attachment.getReports(), false));
        }
        return dto;
    }
    /**
     * Converts a PendingAttachment DTO to a PendingAttachment entity
     *
     * @param dto                     the attachment DTO to be converted
     * @param includeRelativeEntities flag indicating whether to map the sub-DTOs
     * @return the corresponding PendingAttachment entity if the dto parameter is not null, null otherwise
     */
    public static PendingAttachment toEntity(PendingAttachmentDto dto, boolean includeRelativeEntities) {
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
        if (includeRelativeEntities) {
            entity.setPoi(GenericPOIMapper.toEntity(dto.getPoi(), false));
            entity.setItinerary(GenericItineraryMapper.toEntity(dto.getItinerary(), false));
            entity.setReports(ReportMapper.toEntity(dto.getReports(), false));
        }
        return entity;
    }
}
