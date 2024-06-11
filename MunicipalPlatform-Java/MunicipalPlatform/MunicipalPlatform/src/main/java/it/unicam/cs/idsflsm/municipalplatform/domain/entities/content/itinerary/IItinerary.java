package it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.IContent;
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
