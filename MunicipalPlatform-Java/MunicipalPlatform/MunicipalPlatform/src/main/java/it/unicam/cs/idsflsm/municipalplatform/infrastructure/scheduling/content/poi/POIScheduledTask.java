package it.unicam.cs.idsflsm.municipalplatform.infrastructure.scheduling.content.poi;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.content.poi.IPOIRepository;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.scheduling.GenericScheduledTask;
import org.springframework.stereotype.Component;
/**
 * Scheduled task class for managing deletion of POI entity
 */
@Component
public class POIScheduledTask extends GenericScheduledTask<POI> {
    public POIScheduledTask(IPOIRepository poiRepository) {
        super(poiRepository);
    }
    /**
     * Method that deletes pois based on their expiry date
     */
    public void deletePOIsByExpiryDate() {
        this.setMethodName("findByExpiryDate");
        this.deleteEntities();
    }
    /**
     * Method that deletes pois based on their REMOVABLE state
     */
    public void deletePOIsByRemovableState() {
        this.setMethodName("findByRemovableState");
        this.deleteEntities();
    }
}
