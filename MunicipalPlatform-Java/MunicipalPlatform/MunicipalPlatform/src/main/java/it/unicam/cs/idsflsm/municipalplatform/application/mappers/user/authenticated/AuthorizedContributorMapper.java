package it.unicam.cs.idsflsm.municipalplatform.application.mappers.user.authenticated;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.itinerary.GenericItineraryMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.poi.GenericPOIMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.contest.ContestMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthorizedContributorDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthorizedContributor;
/**
 * Utility class for mapping between AuthorizedContributor and AuthorizedContributorDto
 */
public class AuthorizedContributorMapper {
    /**
     * Converts an AuthorizedContributor entity to an AuthorizedContributor DTO
     *
     * @param authorizedContributor   the authorizedContributor entity to be converted
     * @param includeRelativeEntities flag indicating whether to map the sub-entities
     * @return the corresponding AuthorizedContributorDto if the authorizedContributor parameter is not null, null otherwise
     */
    public static AuthorizedContributorDto toDto(AuthorizedContributor authorizedContributor, boolean includeRelativeEntities) {
        if (authorizedContributor == null) {
            return null;
        }
        AuthorizedContributorDto dto = new AuthorizedContributorDto();
        dto.setId(authorizedContributor.getId());
        dto.setUsername(authorizedContributor.getUsername());
        dto.setPassword(authorizedContributor.getPassword());
        dto.setName(authorizedContributor.getName());
        dto.setSurname(authorizedContributor.getSurname());
        dto.setRole(authorizedContributor.getRole());
        if (includeRelativeEntities) {
            dto.setPois(GenericPOIMapper.toDto(authorizedContributor.getPois(), false));
            dto.setItineraries(GenericItineraryMapper.toDto(authorizedContributor.getItineraries(), false));
            dto.setParticipatedContests(ContestMapper.toDto(authorizedContributor.getParticipatedContests(), false));
        }
        return dto;
    }
    /**
     * Converts an AuthorizedContributor DTO to an AuthorizedContributor entity
     *
     * @param dto                     the AuthorizedContributor DTO to be converted
     * @param includeRelativeEntities flag indicating whether to map the sub-DTOs
     * @return the corresponding AuthorizedContributor entity if the dto parameter is not null, null otherwise
     */
    public static AuthorizedContributor toEntity(AuthorizedContributorDto dto, boolean includeRelativeEntities) {
        if (dto == null) {
            return null;
        }
        AuthorizedContributor entity = new AuthorizedContributor();
        entity.setId(dto.getId());
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setRole(dto.getRole());
        if (includeRelativeEntities) {
            entity.setPois(GenericPOIMapper.toEntity(dto.getPois(), false));
            entity.setItineraries(GenericItineraryMapper.toEntity(dto.getItineraries(), false));
            entity.setParticipatedContests(ContestMapper.toEntity(dto.getParticipatedContests(), false));
        }
        return entity;
    }
}

