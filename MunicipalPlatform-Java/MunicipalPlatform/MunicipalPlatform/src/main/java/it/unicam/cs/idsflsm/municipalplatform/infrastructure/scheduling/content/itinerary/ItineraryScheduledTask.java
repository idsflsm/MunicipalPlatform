package it.unicam.cs.idsflsm.municipalplatform.infrastructure.scheduling.content.itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.content.itinerary.IItineraryRepository;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.scheduling.GenericScheduledTask;
import org.springframework.stereotype.Component;
/**
 * Scheduled task class for managing deletion of Itinerary entity
 */
@Component
public class ItineraryScheduledTask extends GenericScheduledTask<Itinerary> {
    public ItineraryScheduledTask(IItineraryRepository itineraryRepository) {
        super(itineraryRepository);
    }
    /**
     * Method that deletes itineraries based on their expiry date
     */
    public void deleteItinerariesByExpiryDate() {
        this.setMethodName("findByExpiryDate");
        this.deleteEntities();
    }
    /**
     * Method that deletes itineraries based on their REMOVABLE STATE
     */
    public void deleteItinerariesByRemovableState() {
        this.setMethodName("findByRemovableState");
        this.deleteEntities();
    }
}
