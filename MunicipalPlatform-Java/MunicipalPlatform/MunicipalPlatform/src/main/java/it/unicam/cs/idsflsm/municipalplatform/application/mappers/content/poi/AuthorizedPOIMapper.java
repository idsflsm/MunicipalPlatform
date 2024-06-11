package it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.poi;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.attachment.GenericAttachmentMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.itinerary.GenericItineraryMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.user.authenticated.GenericAuthenticatedUserMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.AuthorizedPOIDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.AuthorizedPOI;
/**
 * Utility class for mapping between AuthorizedPOI and AuthorizedPOIDto
 */
public class AuthorizedPOIMapper {
    /**
     * Converts an AuthorizedPOI entity to an AuthorizedPOI DTO
     *
     * @param poi                     the POI entity to be converted
     * @param includeRelativeEntities flag indicating whether to map the sub-entities
     * @return the corresponding AuthorizedPOIDto if the poi parameter is not null, null otherwise
     */
    public static AuthorizedPOIDto toDto(AuthorizedPOI poi, boolean includeRelativeEntities) {
        if (poi == null) {
            return null;
        }
        AuthorizedPOIDto dto = new AuthorizedPOIDto();
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
     * Converts an AuthorizedPOI DTO to an AuthorizedPOI entity
     *
     * @param dto                     the POI DTO to be converted
     * @param includeRelativeEntities flag indicating whether to map the sub-DTOs
     * @return the corresponding AuthorizedPOI entity if the dto parameter is not null, null otherwise
     */
    public static AuthorizedPOI toEntity(AuthorizedPOIDto dto, boolean includeRelativeEntities) {
        if (dto == null) {
            return null;
        }
        AuthorizedPOI entity = new AuthorizedPOI();
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

