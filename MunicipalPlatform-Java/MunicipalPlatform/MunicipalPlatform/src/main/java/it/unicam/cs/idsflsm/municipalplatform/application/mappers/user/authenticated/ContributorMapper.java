package it.unicam.cs.idsflsm.municipalplatform.application.mappers.user.authenticated;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.ContributorDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.Contributor;
public class ContributorMapper {
    public static ContributorDto toDto(Contributor contributor) {
        return new ContributorDto(
                contributor.getId(),
                contributor.getUsername(),
                contributor.getPassword(),
                contributor.getName(),
                contributor.getSurname(),
                contributor.getPois(),
                contributor.getItineraries()
        );
    }
    public static Contributor toEntity(ContributorDto contributorDto) {
        return new Contributor(
                contributorDto.getId(),
                contributorDto.getUsername(),
                contributorDto.getPassword(),
                contributorDto.getName(),
                contributorDto.getSurname(),
                contributorDto.getPois(),
                contributorDto.getItineraries()
        );
    }
}
