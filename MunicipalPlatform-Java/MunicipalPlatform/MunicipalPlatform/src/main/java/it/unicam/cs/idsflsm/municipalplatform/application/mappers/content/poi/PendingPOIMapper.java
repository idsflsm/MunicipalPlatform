package it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.poi;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.attachment.GenericAttachmentMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.itinerary.GenericItineraryMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.user.authenticated.AuthenticatedTouristMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.user.authenticated.GenericAuthenticatedUserMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.PendingPOIDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.PendingPOI;
/**
 * Utility class for mapping between PendingPOI and PendingPOIDto
 */
public class PendingPOIMapper {
    /**
     * Converts a PendingPOI entity to a PendingPOI DTO
     * @param poi the POI entity to be converted
     * @param includeRelativeEntities flag indicating whether to map the sub-entities
     * @return the corresponding PendingPOIDto if the poi parameter is not null, null otherwise
     */
    public static PendingPOIDto toDto(PendingPOI poi, boolean includeRelativeEntities) {
        if (poi == null) {
            return null;
        }
        PendingPOIDto dto = new PendingPOIDto();
        dto.setId(poi.getId());
        dto.setName(poi.getName());
        dto.setCoordinates(poi.getCoordinates());
        dto.setDescription(poi.getDescription());
        dto.setAuthor(poi.getAuthor());
        dto.setCreationDate(poi.getCreationDate());
        dto.setExpiryDate(poi.getExpiryDate());
        dto.setState(poi.getState());
        if (includeRelativeEntities) {
            dto.setPoiItineraries(GenericItineraryMapper.toDto(poi.getPoiItineraries(), false));
            dto.setTourists(GenericAuthenticatedUserMapper.toDto(poi.getUsers(), false));
            dto.setAttachments(GenericAttachmentMapper.toDto(poi.getAttachments(), false));
        }
        return dto;
    }
    /**
     * Converts a PendingPOI DTO to a PendingPOI entity
     * @param dto the POI DTO to be converted
     * @param includeRelativeEntities flag indicating whether to map the sub-DTOs
     * @return the corresponding PendingPOI entity if the dto parameter is not null, null otherwise
     */
    public static PendingPOI toEntity(PendingPOIDto dto, boolean includeRelativeEntities) {
        if (dto == null) {
            return null;
        }
        PendingPOI entity = new PendingPOI();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setCoordinates(dto.getCoordinates());
        entity.setDescription(dto.getDescription());
        entity.setAuthor(dto.getAuthor());
        entity.setCreationDate(dto.getCreationDate());
        entity.setExpiryDate(dto.getExpiryDate());
        entity.setState(dto.getState());
        if (includeRelativeEntities) {
            entity.setPoiItineraries(GenericItineraryMapper.toEntity(dto.getPoiItineraries(), false));
            entity.setUsers(GenericAuthenticatedUserMapper.toEntity(dto.getTourists(), false));
            entity.setAttachments(GenericAttachmentMapper.toEntity(dto.getAttachments(), false));
        }

        return entity;
    }
}

