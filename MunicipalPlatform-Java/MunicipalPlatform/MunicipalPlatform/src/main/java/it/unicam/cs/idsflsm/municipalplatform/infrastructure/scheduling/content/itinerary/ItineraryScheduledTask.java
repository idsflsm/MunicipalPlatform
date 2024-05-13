package it.unicam.cs.idsflsm.municipalplatform.infrastructure.scheduling.content.itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.GenericRepository;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.content.itinerary.IItineraryRepository;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.scheduling.GenericScheduledTask;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
public class ItineraryScheduledTask extends GenericScheduledTask<Itinerary> {
    public ItineraryScheduledTask(IItineraryRepository itineraryRepository) {
        super(itineraryRepository);
    }
    public void deleteItinerariesByExpiryDate() {
        this.setMethodName("findByExpiryDate");
        this.deleteEntities();
    }
    public void deleteItinerariesByRemovableState() {
        this.setMethodName("findByRemovableState");
        this.deleteEntities();
    }
}
