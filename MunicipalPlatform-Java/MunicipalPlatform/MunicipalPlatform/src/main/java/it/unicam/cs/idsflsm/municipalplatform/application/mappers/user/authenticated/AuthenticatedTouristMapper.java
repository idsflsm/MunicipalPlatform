package it.unicam.cs.idsflsm.municipalplatform.application.mappers.user.authenticated;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.itinerary.GenericItineraryMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.poi.GenericPOIMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.contest.ContestMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedTouristDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedTourist;

import java.util.List;
import java.util.stream.Collectors;
public class AuthenticatedTouristMapper {
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
        } else {
            return null;
        }
    }
    public static AuthenticatedTourist toEntity(AuthenticatedTouristDto authenticatedTouristDto, boolean includeRelativeEntities) {
        if (authenticatedTouristDto != null) {
            AuthenticatedTourist entity = new AuthenticatedTourist();
            entity.setId(authenticatedTouristDto.getId());
            entity.setUsername(authenticatedTouristDto.getUsername());
            entity.setPassword(authenticatedTouristDto.getPassword());
            entity.setName(authenticatedTouristDto.getName());
            entity.setSurname(authenticatedTouristDto.getSurname());
            entity.setRole(authenticatedTouristDto.getRole());
            if (includeRelativeEntities) {
                entity.setPois( GenericPOIMapper.toEntity(authenticatedTouristDto.getPois(), false));
                entity.setItineraries(GenericItineraryMapper.toEntity(authenticatedTouristDto.getItineraries(), false));
                entity.setParticipatedContests(ContestMapper.toEntity(authenticatedTouristDto.getParticipatedContests(), false));
            }
            return entity;
        } else {
            return null;
        }
    }
    public static List<AuthenticatedTouristDto> toDto(List<AuthenticatedTourist> authenticatedTourists, boolean includeRelativeEntities) {
        if (authenticatedTourists != null) {
            return authenticatedTourists.stream().map(authenticatedTourist -> AuthenticatedTouristMapper.toDto(authenticatedTourist, includeRelativeEntities)).collect(Collectors.toList());
        } else {
            return null;
        }
    }
    public static List<AuthenticatedTourist> toEntity(List<AuthenticatedTouristDto> authenticatedTouristDtos, boolean includeRelativeEntities) {
        if (authenticatedTouristDtos != null) {
            return authenticatedTouristDtos.stream().map(authenticatedTouristDto -> AuthenticatedTouristMapper.toEntity(authenticatedTouristDto, includeRelativeEntities)).collect(Collectors.toList());
        } else {
            return null;
        }
    }
}
