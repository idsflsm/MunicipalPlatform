package it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.ItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.POIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.contest.ContestDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserRole;

import java.util.List;
import java.util.UUID;
public class AuthorizedContributorDto extends AuthenticatedUserDto {
    public AuthorizedContributorDto() {
    }
    public AuthorizedContributorDto(String username, String password, String name, String surname, List<POIDto> pois, List<ItineraryDto> itineraries, List<ContestDto> participatedContests, UserRole role) {
        super(username, password, name, surname, pois, itineraries, participatedContests, role);
    }
}
