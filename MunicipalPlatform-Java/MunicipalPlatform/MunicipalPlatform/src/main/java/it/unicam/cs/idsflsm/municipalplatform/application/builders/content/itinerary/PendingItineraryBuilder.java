package it.unicam.cs.idsflsm.municipalplatform.application.builders.content.itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.PendingItinerary;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
/**
 * Specific builder class for the building of PendingItinerary instances
 */
@Component
@Getter
@Setter
@NoArgsConstructor
public class PendingItineraryBuilder extends ItineraryBuilder {
    @Override
    public Itinerary build() {
        return new PendingItinerary(
                this.getName(),
                this.getCoordinates(),
                this.getDescription(),
                this.getAuthor(),
                this.getCreationDate(),
                this.getExpiryDate(),
                this.getContentState(),
                this.getItineraryPois(),
                this.getUsers(),
                this.getAttachments(),
                this.getContribution()
        );
    }
}
