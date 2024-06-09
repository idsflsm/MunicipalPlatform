package it.unicam.cs.idsflsm.municipalplatform.application.mappers.user.authenticated;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.itinerary.GenericItineraryMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.poi.GenericPOIMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.contest.ContestMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.ContributorDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.Contributor;
/**
 * Utility class for mapping between Contributor and ContributorDto
 */
public class ContributorMapper {
    /**
     * Converts a Contributor entity to a Contributor DTO
     * @param contributor the contributor entity to be converted
     * @param includeRelativeEntities flag indicating whether to map the sub-entities
     * @return the corresponding ContributorDto if the contributor parameter is not null, null otherwise
     */
    public static ContributorDto toDto(Contributor contributor, boolean includeRelativeEntities) {
        if (contributor == null) {
            return null;
        }
        ContributorDto dto = new ContributorDto();
        dto.setId(contributor.getId());
        dto.setUsername(contributor.getUsername());
        dto.setPassword(contributor.getPassword());
        dto.setName(contributor.getName());
        dto.setSurname(contributor.getSurname());
        dto.setRole(contributor.getRole());
        if (includeRelativeEntities) {
            dto.setPois(GenericPOIMapper.toDto(contributor.getPois(), false));
            dto.setItineraries(GenericItineraryMapper.toDto(contributor.getItineraries(), false));
            dto.setParticipatedContests(ContestMapper.toDto(contributor.getParticipatedContests(), false));
        }
        return dto;
    }
    /**
     * Converts a Contributor DTO to a Contributor entity
     * @param dto the Contributor DTO to be converted
     * @param includeRelativeEntities flag indicating whether to map the sub-DTOs
     * @return the corresponding Contributor entity if the dto parameter is not null, null otherwise
     */
    public static Contributor toEntity(ContributorDto dto, boolean includeRelativeEntities) {
        if (dto == null) {
            return null;
        }
        Contributor entity = new Contributor();
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

