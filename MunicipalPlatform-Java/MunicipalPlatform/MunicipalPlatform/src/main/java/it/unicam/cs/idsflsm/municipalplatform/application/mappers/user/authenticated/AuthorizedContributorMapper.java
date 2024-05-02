package it.unicam.cs.idsflsm.municipalplatform.application.mappers.user.authenticated;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthorizedContributorDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthorizedContributor;
public class AuthorizedContributorMapper {
    public static AuthorizedContributorDto toDto(AuthorizedContributor authorizedContributor) {
        return new AuthorizedContributorDto(
                authorizedContributor.getId(),
                authorizedContributor.getUsername(),
                authorizedContributor.getPassword(),
                authorizedContributor.getName(),
                authorizedContributor.getSurname(),
                authorizedContributor.getPois(),
                authorizedContributor.getItineraries()
        );
    }
    public static AuthorizedContributor toEntity(AuthorizedContributorDto authorizedContributorDto) {
        return new AuthorizedContributor(
                authorizedContributorDto.getId(),
                authorizedContributorDto.getUsername(),
                authorizedContributorDto.getPassword(),
                authorizedContributorDto.getName(),
                authorizedContributorDto.getSurname(),
                authorizedContributorDto.getPois(),
                authorizedContributorDto.getItineraries()
        );
    }
}
