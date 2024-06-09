package it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
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
 * Represents an itinerary on the platform, acting as AuthorizedItinerary
 */
@Entity
@DiscriminatorValue("authorized_itinerary")
public class AuthorizedItinerary extends Itinerary {
    public AuthorizedItinerary() {
        this.setState(ContentState.UPLOADABLE);
    }
    public AuthorizedItinerary(String name, Coordinates coordinates, String description, String author, Date creationDate, Date expiryDate, ContentState state, List<POI> itineraryPois, List<AuthenticatedUser> users, List<Attachment> attachments, Contribution contribution) {
        super(name, coordinates, description, author, creationDate, expiryDate, state, itineraryPois, users, attachments, contribution);
    }
}
