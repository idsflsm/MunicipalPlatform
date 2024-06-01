package it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;

import java.util.UUID;

public interface IAttachment {
    void setNotNullPoi(POI poi);
    void setNotNullItinerary(Itinerary itinerary);
    void detachFromEntities();
}
