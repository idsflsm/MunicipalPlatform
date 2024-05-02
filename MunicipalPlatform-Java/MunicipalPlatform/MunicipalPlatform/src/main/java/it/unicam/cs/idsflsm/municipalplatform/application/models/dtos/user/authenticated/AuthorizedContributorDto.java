package it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.POI;

import java.util.List;
import java.util.UUID;
public class AuthorizedContributorDto extends AuthenticatedUserDto {
    public AuthorizedContributorDto() {
    }
    public AuthorizedContributorDto(UUID id, String username, String password, String name, String surname, List<POI> pois, List<Itinerary> itineraries) {
        super(id, username, password, name, surname, pois, itineraries);
    }
}
