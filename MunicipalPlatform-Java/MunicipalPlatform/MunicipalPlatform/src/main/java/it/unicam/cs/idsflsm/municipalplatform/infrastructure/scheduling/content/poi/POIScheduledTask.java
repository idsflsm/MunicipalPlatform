package it.unicam.cs.idsflsm.municipalplatform.infrastructure.scheduling.content.poi;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.content.poi.IPOIRepository;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.scheduling.GenericScheduledTask;
import org.springframework.stereotype.Component;
@Component
public class POIScheduledTask extends GenericScheduledTask<POI> {
    public POIScheduledTask(IPOIRepository poiRepository) {
        super(poiRepository);
    }
    public void deletePOIsByExpiryDate() {
        this.setMethodName("findByExpiryDate");
        this.deleteEntities();
    }
    public void deletePOIsByRemovableState() {
        this.setMethodName("findByRemovableState");
        this.deleteEntities();
    }
}
