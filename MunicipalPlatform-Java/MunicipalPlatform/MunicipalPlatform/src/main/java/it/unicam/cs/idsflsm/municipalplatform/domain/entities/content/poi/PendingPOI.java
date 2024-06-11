package it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contribution;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Coordinates;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.List;
/**
 * Represents a Point Of Interest on the platform, acting as PendingPOI
 */
@Entity
@DiscriminatorValue("pending_poi")
public class PendingPOI extends POI {
    public PendingPOI() {
        this.setState(ContentState.VALIDABLE);
    }
    public PendingPOI(String name, Coordinates coordinates, String description, String author, Date creationDate, Date expiryDate, ContentState state, List<Itinerary> poiItineraries, List<AuthenticatedUser> users, List<Attachment> attachments, Contribution contribution) {
        super(name, coordinates, description, author, creationDate, expiryDate, state, poiItineraries, users, attachments, contribution);
    }
}
