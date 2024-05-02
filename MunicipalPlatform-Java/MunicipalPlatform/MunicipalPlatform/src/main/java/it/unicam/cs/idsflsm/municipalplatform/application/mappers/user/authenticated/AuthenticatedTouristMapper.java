package it.unicam.cs.idsflsm.municipalplatform.application.mappers.user.authenticated;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedTouristDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedTourist;
public class AuthenticatedTouristMapper {
    public static AuthenticatedTouristDto toDto(AuthenticatedTourist authenticatedTourist) {
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
    }
    public static AuthenticatedTourist toEntity(AuthenticatedTouristDto authenticatedTouristDto) {
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
    }
}
