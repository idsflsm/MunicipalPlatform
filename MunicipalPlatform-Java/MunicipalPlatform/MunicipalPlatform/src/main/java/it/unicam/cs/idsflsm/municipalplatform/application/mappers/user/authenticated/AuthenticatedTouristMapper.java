package it.unicam.cs.idsflsm.municipalplatform.application.mappers.user.authenticated;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedTouristDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedTourist;

import java.util.List;
import java.util.stream.Collectors;
public class AuthenticatedTouristMapper {
    public static AuthenticatedTouristDto toDto(AuthenticatedTourist authenticatedTourist) {
        if (authenticatedTourist != null) {
            return new AuthenticatedTouristDto(
                    authenticatedTourist.getId(),
                    authenticatedTourist.getUsername(),
                    authenticatedTourist.getPassword(),
                    authenticatedTourist.getName(),
                    authenticatedTourist.getSurname(),
                    authenticatedTourist.getPois(),
                    authenticatedTourist.getItineraries(),
                    authenticatedTourist.getParticipatedContests()
            );
        } else {
            return null;
        }
    }
    public static AuthenticatedTourist toEntity(AuthenticatedTouristDto authenticatedTouristDto) {
        if (authenticatedTouristDto != null) {
            return new AuthenticatedTourist(
                    authenticatedTouristDto.getId(),
                    authenticatedTouristDto.getUsername(),
                    authenticatedTouristDto.getPassword(),
                    authenticatedTouristDto.getName(),
                    authenticatedTouristDto.getSurname(),
                    authenticatedTouristDto.getPois(),
                    authenticatedTouristDto.getItineraries(),
                    authenticatedTouristDto.getParticipatedContests()
            );
        } else {
            return null;
        }
    }
    public static List<AuthenticatedTouristDto> toDto(List<AuthenticatedTourist> authenticatedTourists) {
        if (authenticatedTourists != null) {
            return authenticatedTourists.stream().map(AuthenticatedTouristMapper::toDto).collect(Collectors.toList());
        } else {
            return null;
        }
    }
    public static List<AuthenticatedTourist> toEntity(List<AuthenticatedTouristDto> authenticatedTouristDtos) {
        if (authenticatedTouristDtos != null) {
            return authenticatedTouristDtos.stream().map(AuthenticatedTouristMapper::toEntity).collect(Collectors.toList());
        } else {
            return null;
        }
    }
}
