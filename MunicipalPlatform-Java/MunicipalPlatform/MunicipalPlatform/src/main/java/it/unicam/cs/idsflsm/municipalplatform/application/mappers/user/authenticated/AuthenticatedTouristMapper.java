package it.unicam.cs.idsflsm.municipalplatform.application.mappers.user.authenticated;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.itinerary.GenericItineraryMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.poi.GenericPOIMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.contest.ContestMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedTouristDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedTourist;

import java.util.List;
import java.util.stream.Collectors;
/**
 * Utility class for mapping between AuthenticatedTourist and AuthenticatedTouristDto
 */
public class AuthenticatedTouristMapper {
    /**
     * Converts an AuthenticatedTourist entity to an AuthenticatedTourist DTO
     * @param authenticatedTourist the authenticatedTourist entity to be converted
     * @param includeRelativeEntities flag indicating whether to map the sub-entities
     * @return the corresponding AuthenticatedTouristDto if the authenticatedTourist parameter is not null, null otherwise
     */
    public static AuthenticatedTouristDto toDto(AuthenticatedTourist authenticatedTourist, boolean includeRelativeEntities) {
        if (authenticatedTourist != null) {
            AuthenticatedTouristDto dto = new AuthenticatedTouristDto();
            dto.setId(authenticatedTourist.getId());
            dto.setUsername(authenticatedTourist.getUsername());
            dto.setPassword(authenticatedTourist.getPassword());
            dto.setName(authenticatedTourist.getName());
            dto.setSurname(authenticatedTourist.getSurname());
            dto.setRole(authenticatedTourist.getRole());
            if (includeRelativeEntities) {
                dto.setPois(GenericPOIMapper.toDto(authenticatedTourist.getPois(), false));
                dto.setItineraries(GenericItineraryMapper.toDto(authenticatedTourist.getItineraries(), false));
                dto.setParticipatedContests(ContestMapper.toDto(authenticatedTourist.getParticipatedContests(), false));
            }
            return dto;
        }
        return null;
    }
    /**
     * Converts an AuthenticatedTourist DTO to an AuthenticatedTourist entity
     * @param dto the AuthenticatedTourist DTO to be converted
     * @param includeRelativeEntities flag indicating whether to map the sub-DTOs
     * @return the corresponding AuthenticatedTourist entity if the dto parameter is not null, null otherwise
     */
    public static AuthenticatedTourist toEntity(AuthenticatedTouristDto dto, boolean includeRelativeEntities) {
        if (dto != null) {
            AuthenticatedTourist entity = new AuthenticatedTourist();
            entity.setId(dto.getId());
            entity.setUsername(dto.getUsername());
            entity.setPassword(dto.getPassword());
            entity.setName(dto.getName());
            entity.setSurname(dto.getSurname());
            entity.setRole(dto.getRole());
            if (includeRelativeEntities) {
                entity.setPois( GenericPOIMapper.toEntity(dto.getPois(), false));
                entity.setItineraries(GenericItineraryMapper.toEntity(dto.getItineraries(), false));
                entity.setParticipatedContests(ContestMapper.toEntity(dto.getParticipatedContests(), false));
            }
            return entity;
        }
        return null;
    }
//    public static List<AuthenticatedTouristDto> toDto(List<AuthenticatedTourist> authenticatedTourists, boolean includeRelativeEntities) {
//        if (authenticatedTourists != null) {
//            return authenticatedTourists.stream().map(authenticatedTourist -> AuthenticatedTouristMapper.toDto(authenticatedTourist, includeRelativeEntities)).collect(Collectors.toList());
//        } else {
//            return null;
//        }
//    }
//    public static List<AuthenticatedTourist> toEntity(List<AuthenticatedTouristDto> authenticatedTouristDtos, boolean includeRelativeEntities) {
//        if (authenticatedTouristDtos != null) {
//            return authenticatedTouristDtos.stream().map(authenticatedTouristDto -> AuthenticatedTouristMapper.toEntity(authenticatedTouristDto, includeRelativeEntities)).collect(Collectors.toList());
//        } else {
//            return null;
//        }
//    }
}
