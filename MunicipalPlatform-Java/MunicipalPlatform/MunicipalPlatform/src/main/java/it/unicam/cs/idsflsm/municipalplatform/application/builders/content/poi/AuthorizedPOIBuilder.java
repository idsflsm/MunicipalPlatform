package it.unicam.cs.idsflsm.municipalplatform.application.builders.content.poi;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.AuthorizedPOI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
/**
 * Specific builder class for the building of AuthorizedPOI instances
 */
@Component
@Getter
@Setter
@NoArgsConstructor
public class AuthorizedPOIBuilder extends POIBuilder {
    @Override
    public POI build() {
        return new AuthorizedPOI(
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
