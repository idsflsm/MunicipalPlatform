package it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.itinerary;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.attachment.GenericAttachmentMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.poi.GenericPOIMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.user.authenticated.GenericAuthenticatedUserMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.AuthorizedItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.AuthorizedItinerary;
/**
 * Utility class for mapping between AuthorizedItinerary and AuthorizedItineraryDto
 */
public class AuthorizedItineraryMapper {
    /**
     * Converts an AuthorizedItinerary entity to an AuthorizedItinerary DTO
     *
     * @param itinerary               the itinerary entity to be converted
     * @param includeRelativeEntities flag indicating whether to map the sub-entities
     * @return the corresponding AuthorizedItineraryDto if the itinerary parameter is not null, null otherwise
     */
    public static AuthorizedItineraryDto toDto(AuthorizedItinerary itinerary, boolean includeRelativeEntities) {
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
        if (includeRelativeEntities) {
            dto.setItineraryPois(GenericPOIMapper.toDto(itinerary.getItineraryPois(), false));
            dto.setUsers(GenericAuthenticatedUserMapper.toDto(itinerary.getUsers(), false));
            dto.setAttachments(GenericAttachmentMapper.toDto(itinerary.getAttachments(), false));
        }
        return dto;
    }
    /**
     * Converts an AuthorizedItinerary DTO to an AuthorizedItinerary entity
     *
     * @param dto                     the itinerary DTO to be converted
     * @param includeRelativeEntities flag indicating whether to map the sub-DTOs
     * @return the corresponding AuthorizedItinerary entity if the dto parameter is not null, null otherwise
     */
    public static AuthorizedItinerary toEntity(AuthorizedItineraryDto dto, boolean includeRelativeEntities) {
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
        if (includeRelativeEntities) {
            entity.setItineraryPois(GenericPOIMapper.toEntity(dto.getItineraryPois(), false));
            entity.setUsers(GenericAuthenticatedUserMapper.toEntity(dto.getUsers(), false));
            entity.setAttachments(GenericAttachmentMapper.toEntity(dto.getAttachments(), false));
        }
        return entity;
    }
}
