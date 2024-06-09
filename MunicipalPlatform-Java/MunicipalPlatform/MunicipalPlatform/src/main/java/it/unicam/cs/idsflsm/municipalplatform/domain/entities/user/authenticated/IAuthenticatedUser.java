package it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;

import java.util.List;

/**
 * Represents an authenticated user on the platform. Provides methods to execute
 * the command associated to the user, and to detach user entity from relationships
 * with other entities, in order to manage entity persistence
 */
public interface IAuthenticatedUser {
    /**
     * Method that enables executing the command associated
     * to the user itself
     */
    void executeCommand();
    /**
     * Method for detachment of user entity
     * from relationships with other entities
     */
    void detachFromEntities();
}
