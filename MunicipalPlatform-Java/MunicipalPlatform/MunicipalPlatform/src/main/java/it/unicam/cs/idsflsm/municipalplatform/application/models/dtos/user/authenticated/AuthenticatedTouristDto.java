package it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contest;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
public class AuthenticatedTouristDto extends AuthenticatedUserDto {
    private List<Contest> participatedContests = new ArrayList<>();
    public AuthenticatedTouristDto() {
    }
    public AuthenticatedTouristDto(UUID id, String username, String password, String name, String surname, List<POI> pois, List<Itinerary> itineraries, List<Contest> participatedContests) {
        super(id, username, password, name, surname, pois, itineraries);
        this.participatedContests = participatedContests;
    }
}
