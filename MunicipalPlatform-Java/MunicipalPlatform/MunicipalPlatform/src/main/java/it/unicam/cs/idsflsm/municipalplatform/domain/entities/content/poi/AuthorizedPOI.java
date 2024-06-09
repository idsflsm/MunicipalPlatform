package it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contribution;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedTourist;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Coordinates;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.List;
import java.util.UUID;
/**
 * Represents a Point Of Interest on the platform, acting as AuthorizedPOI
 */
@Entity
@DiscriminatorValue("authorized_poi")
public class AuthorizedPOI extends POI {
    public AuthorizedPOI() {
        this.setState(ContentState.UPLOADABLE);
    }
    public AuthorizedPOI(String name, Coordinates coordinates, String description, String author, Date creationDate, Date expiryDate, ContentState state, List<Itinerary> poiItineraries, List<AuthenticatedUser> users, List<Attachment> attachments, Contribution contribution) {
        super(name, coordinates, description, author, creationDate, expiryDate, state, poiItineraries, users, attachments, contribution);
    }
}
