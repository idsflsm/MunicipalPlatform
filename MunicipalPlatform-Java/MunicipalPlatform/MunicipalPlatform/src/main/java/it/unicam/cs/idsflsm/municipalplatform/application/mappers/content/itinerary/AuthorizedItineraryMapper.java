package it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.itinerary;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.attachment.GenericAttachmentMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.poi.GenericPOIMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.user.authenticated.GenericAuthenticatedUserMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.AuthorizedItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.AuthorizedItinerary;
public class AuthorizedItineraryMapper {

    public static AuthorizedItineraryDto toDto(AuthorizedItinerary itinerary, boolean includeRelatedEntities) {
        if (itinerary == null) {
            return null;
        }

        AuthorizedItineraryDto dto = new AuthorizedItineraryDto();
        dto.setId(itinerary.getId());
        dto.setName(itinerary.getName());
        dto.setCoordinates(itinerary.getCoordinates());
        dto.setDescription(itinerary.getDescription());
        dto.setAuthor(itinerary.getAuthor());
        dto.setCreationDate(itinerary.getCreationDate());
        dto.setExpiryDate(itinerary.getExpiryDate());
        dto.setState(itinerary.getState());

        if (includeRelatedEntities) {
            dto.setItineraryPois(GenericPOIMapper.toDto(itinerary.getItineraryPois(), false));
            dto.setUsers(GenericAuthenticatedUserMapper.toDto(itinerary.getUsers(), false));
            dto.setAttachments(GenericAttachmentMapper.toDto(itinerary.getAttachments(), false));
        }

        return dto;
    }

    public static AuthorizedItinerary toEntity(AuthorizedItineraryDto dto, boolean includeRelatedEntities) {
        if (dto == null) {
            return null;
        }

        AuthorizedItinerary entity = new AuthorizedItinerary();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setCoordinates(dto.getCoordinates());
        entity.setDescription(dto.getDescription());
        entity.setAuthor(dto.getAuthor());
        entity.setCreationDate(dto.getCreationDate());
        entity.setExpiryDate(dto.getExpiryDate());
        entity.setState(dto.getState());

        if (includeRelatedEntities) {
            entity.setItineraryPois(GenericPOIMapper.toEntity(dto.getItineraryPois(), false));
            entity.setUsers(GenericAuthenticatedUserMapper.toEntity(dto.getUsers(), false));
            entity.setAttachments(GenericAttachmentMapper.toEntity(dto.getAttachments(), false));
        }

        return entity;
    }
}
