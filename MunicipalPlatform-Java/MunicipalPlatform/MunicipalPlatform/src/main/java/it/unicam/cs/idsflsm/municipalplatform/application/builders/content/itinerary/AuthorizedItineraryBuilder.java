package it.unicam.cs.idsflsm.municipalplatform.application.builders.content.itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.AuthorizedItinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contribution;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Coordinates;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
/**
 * Specific builder class for the building of AuthorizedItinerary instances
 */
@Component
@Getter
@Setter
@NoArgsConstructor
public class AuthorizedItineraryBuilder extends ItineraryBuilder {
    @Override
    public Itinerary build() {
        return new AuthorizedItinerary(
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
