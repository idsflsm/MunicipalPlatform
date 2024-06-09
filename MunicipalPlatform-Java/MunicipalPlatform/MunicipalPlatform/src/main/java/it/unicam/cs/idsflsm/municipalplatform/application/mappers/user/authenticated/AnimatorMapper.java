package it.unicam.cs.idsflsm.municipalplatform.application.mappers.user.authenticated;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.itinerary.GenericItineraryMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.poi.GenericPOIMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.contest.ContestMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AnimatorDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.Animator;
/**
 * Utility class for mapping between Animator and AnimatorDto
 */
public class AnimatorMapper {
    /**
     * Converts an Animator entity to an Animator DTO
     * @param animator the animator entity to be converted
     * @param includeRelativeEntities flag indicating whether to map the sub-entities
     * @return the corresponding AnimatorDto if the animator parameter is not null, null otherwise
     */
    public static AnimatorDto toDto(Animator animator, boolean includeRelativeEntities) {
        if (animator == null) {
            return null;
        }
        AnimatorDto dto = new AnimatorDto();
        dto.setId(animator.getId());
        dto.setUsername(animator.getUsername());
        dto.setPassword(animator.getPassword());
        dto.setName(animator.getName());
        dto.setSurname(animator.getSurname());
        dto.setRole(animator.getRole());
        if (includeRelativeEntities) {
            dto.setPois(GenericPOIMapper.toDto(animator.getPois(), false));
            dto.setItineraries(GenericItineraryMapper.toDto(animator.getItineraries(), false));
            dto.setParticipatedContests(ContestMapper.toDto(animator.getParticipatedContests(), false));
        }
        return dto;
    }
    /**
     * Converts an Animator DTO to an Animator entity
     * @param dto the Animator DTO to be converted
     * @param includeRelativeEntities flag indicating whether to map the sub-DTOs
     * @return the corresponding Animator entity if the dto parameter is not null, null otherwise
     */
    public static Animator toEntity(AnimatorDto dto, boolean includeRelativeEntities) {
        if (dto == null) {
            return null;
        }
        Animator entity = new Animator();
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
