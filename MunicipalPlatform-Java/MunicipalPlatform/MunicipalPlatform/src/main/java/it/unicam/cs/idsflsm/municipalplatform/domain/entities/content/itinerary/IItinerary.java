package it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.IContent;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedTourist;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;

import java.util.List;
/**
 * Represents an itinerary on the platform. Provides method to detach itinerary entity
 * from relationships with other entities, in order to manage entity persistence
 */
public interface IItinerary extends IContent {
    /**
     * Method for detachment of itinerary entity
     * from relationships with other entities
     */
    void detachFromEntities();
}
