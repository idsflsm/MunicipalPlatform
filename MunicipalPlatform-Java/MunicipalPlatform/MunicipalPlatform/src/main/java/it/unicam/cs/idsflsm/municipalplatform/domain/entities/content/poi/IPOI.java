package it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.IContent;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedTourist;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;

import java.util.List;

/**
 * Represents a Point Of Interest on the platform. Provides method to detach poi entity
 * from relationships with other entities, in order to manage entity persistence
 */
public interface IPOI extends IContent {
    /**
     * Method for detachment of poi entity
     * from relationships with other entities
     */
    void detachFromEntities();
}
