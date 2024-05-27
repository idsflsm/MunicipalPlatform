package it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.poi;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.attachment.GenericAttachmentMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.itinerary.GenericItineraryMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.user.authenticated.AuthenticatedTouristMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.user.authenticated.GenericAuthenticatedUserMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.AuthorizedPOIDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.AuthorizedPOI;
public class AuthorizedPOIMapper {

    public static AuthorizedPOIDto toDto(AuthorizedPOI poi, boolean includeRelatedEntities) {
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

        if (includeRelatedEntities) {
            dto.setPoiItineraries(GenericItineraryMapper.toDto(poi.getPoiItineraries(), false));
            dto.setTourists(GenericAuthenticatedUserMapper.toDto(poi.getUsers(), false));
            dto.setAttachments(GenericAttachmentMapper.toDto(poi.getAttachments(), false));
        }

        return dto;
    }

    public static AuthorizedPOI toEntity(AuthorizedPOIDto poiDto, boolean includeRelatedEntities) {
        if (poiDto == null) {
            return null;
        }

        AuthorizedPOI entity = new AuthorizedPOI();
        entity.setId(poiDto.getId());
        entity.setName(poiDto.getName());
        entity.setCoordinates(poiDto.getCoordinates());
        entity.setDescription(poiDto.getDescription());
        entity.setAuthor(poiDto.getAuthor());
        entity.setCreationDate(poiDto.getCreationDate());
        entity.setExpiryDate(poiDto.getExpiryDate());
        entity.setState(poiDto.getState());

        if (includeRelatedEntities) {
            entity.setPoiItineraries(GenericItineraryMapper.toEntity(poiDto.getPoiItineraries(), false));
            entity.setUsers(GenericAuthenticatedUserMapper.toEntity(poiDto.getTourists(), false));
            entity.setAttachments(GenericAttachmentMapper.toEntity(poiDto.getAttachments(), false));
        }

        return entity;
    }
}

