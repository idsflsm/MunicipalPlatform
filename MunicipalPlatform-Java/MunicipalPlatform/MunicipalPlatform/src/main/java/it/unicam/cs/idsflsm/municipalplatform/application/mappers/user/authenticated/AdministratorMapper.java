package it.unicam.cs.idsflsm.municipalplatform.application.mappers.user.authenticated;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.itinerary.GenericItineraryMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.poi.GenericPOIMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.contest.ContestMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AdministratorDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.Administrator;
/**
 * Utility class for mapping between Administrator and AdministratorDto
 */
public class AdministratorMapper {
    /**
     * Converts an Administrator entity to an Administrator DTO
     *
     * @param administrator           the administrator entity to be converted
     * @param includeRelativeEntities flag indicating whether to map the sub-entities
     * @return the corresponding AdministratorDto if the administrator parameter is not null, null otherwise
     */
    public static AdministratorDto toDto(Administrator administrator, boolean includeRelativeEntities) {
        if (administrator == null) {
            return null;
        }
        AdministratorDto dto = new AdministratorDto();
        dto.setId(administrator.getId());
        dto.setUsername(administrator.getUsername());
        dto.setPassword(administrator.getPassword());
        dto.setName(administrator.getName());
        dto.setSurname(administrator.getSurname());
        dto.setRole(administrator.getRole());
        if (includeRelativeEntities) {
            dto.setPois(GenericPOIMapper.toDto(administrator.getPois(), false));
            dto.setItineraries(GenericItineraryMapper.toDto(administrator.getItineraries(), false));
            dto.setParticipatedContests(ContestMapper.toDto(administrator.getParticipatedContests(), false));
        }
        return dto;
    }
    /**
     * Converts an Administrator DTO to an Administrator entity
     *
     * @param dto                     the Administrator DTO to be converted
     * @param includeRelativeEntities flag indicating whether to map the sub-DTOs
     * @return the corresponding Administrator entity if the dto parameter is not null, null otherwise
     */
    public static Administrator toEntity(AdministratorDto dto, boolean includeRelativeEntities) {
        if (dto == null) {
            return null;
        }
        Administrator entity = new Administrator();
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
