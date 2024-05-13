package it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
@Entity
@Getter
@Setter
@DiscriminatorValue("authenticated_tourist")
public class AuthenticatedTourist extends AuthenticatedUser {
    @ManyToMany(mappedBy = "participatingTourists")
    private List<Contest> participatedContests;
    public AuthenticatedTourist() {
    }
    public AuthenticatedTourist(UUID id, String username, String password, String name, String surname, List<POI> pois, List<Itinerary> itineraries, List<Contest> participatedContests) {
        super(id, username, password, name, surname, pois, itineraries);
        this.participatedContests = participatedContests;
    }
}
