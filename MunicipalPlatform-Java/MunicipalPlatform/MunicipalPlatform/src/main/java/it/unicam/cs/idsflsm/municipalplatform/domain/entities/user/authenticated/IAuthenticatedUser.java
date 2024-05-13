package it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;

import java.util.List;
public interface IAuthenticatedUser {
    String getUsername();
    void setUsername(String username);
    String getPassword();
    void setPassword(String password);
    String getName();
    void setName(String name);
    String getSurname();
    void setSurname(String surname);
    List<POI> getPois();
    void setPois(List<POI> pois);
    List<Itinerary> getItineraries();
    void setItineraries(List<Itinerary> itineraries);
}
