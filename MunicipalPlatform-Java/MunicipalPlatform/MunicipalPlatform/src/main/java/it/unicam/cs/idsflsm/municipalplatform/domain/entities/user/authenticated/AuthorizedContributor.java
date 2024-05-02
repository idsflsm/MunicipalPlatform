package it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.POI;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.List;
import java.util.UUID;
@Entity
@DiscriminatorValue("authorized_contributor")
public class AuthorizedContributor extends AuthenticatedUser {
    public AuthorizedContributor() {
    }
    public AuthorizedContributor(UUID id, String username, String password, String name, String surname, List<POI> pois, List<Itinerary> itineraries) {
        super(id, username, password, name, surname, pois, itineraries);
    }
}
