package it.unicam.cs.idsflsm.municipalplatform.application.builders.user;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contest;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserRole;

import java.util.List;
public interface IAuthenticatedUserBuilder {
    void setUsername(String username);
    void setPassword(String password);
    void setName(String name);
    void setSurname(String surname);
    void setPois(List<POI> pois);
    void setItineraries(List<Itinerary> itineraries);
    void setParticipatedContests(List<Contest> participatedContests);
    void setRole(UserRole role);
    AuthenticatedUser build();
}
