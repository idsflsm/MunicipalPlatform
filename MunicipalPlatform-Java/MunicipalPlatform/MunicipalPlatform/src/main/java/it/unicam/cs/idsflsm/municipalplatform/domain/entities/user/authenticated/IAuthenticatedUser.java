package it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;

import java.util.List;
public interface IAuthenticatedUser {
    void executeCommand();

    void detachFromEntities();
}
