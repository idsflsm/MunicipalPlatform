package it.unicam.cs.idsflsm.municipalplatform.application.abstractions.handlers.content.itinerary;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.ItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserPermission;
public interface IItineraryHandler {
    /**
     * Method for modifying an Itinerary entity, based on the provided
     * Itinerary DTO and user permission
     *
     * @param itineraryDto the Itinerary DTO containing new information
     * @param permission   the user permission determining the correct builder instance
     * @return the modified itinerary entity
     */
    Itinerary modifyItinerary(ItineraryDto itineraryDto, UserPermission permission);
}
