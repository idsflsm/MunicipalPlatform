package it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;

import java.util.UUID;

/**
 * Represents an attachment, associated to a content on the platform. Provides methods to
 * set the associated content (POI or Itinerary), and to detach attachment entity
 * from relationships with other entities, in order to manage entity persistence
 */
public interface IAttachment {
    /**
     * Method to set the associated poi, if it is already not null
     * @param poi new POI instance to set as active
     */
    void setNotNullPoi(POI poi);
    /**
     * Method to set the associated itinerary, if it is already not null
     * @param itinerary new Itinerary instance to set as active
     */
    void setNotNullItinerary(Itinerary itinerary);
    /**
     * Method for detachment of attachment entity
     * from relationships with other entities
     */
    void detachFromEntities();
}
