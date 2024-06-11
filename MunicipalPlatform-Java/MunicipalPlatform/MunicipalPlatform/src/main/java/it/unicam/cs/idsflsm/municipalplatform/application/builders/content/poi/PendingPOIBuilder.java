package it.unicam.cs.idsflsm.municipalplatform.application.builders.content.poi;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.PendingPOI;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
/**
 * Specific builder class for the building of PendingPOI instances
 */
@Component
@Getter
@Setter
@NoArgsConstructor
public class PendingPOIBuilder extends POIBuilder {
    @Override
    public POI build() {
        return new PendingPOI(
                this.getName(),
                this.getCoordinates(),
                this.getDescription(),
                this.getAuthor(),
                this.getCreationDate(),
                this.getExpiryDate(),
                this.getContentState(),
                this.getPoiItineraries(),
                this.getUsers(),
                this.getAttachments(),
                this.getContribution()
        );
    }
}
