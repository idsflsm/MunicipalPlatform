package it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedTourist;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Coordinates;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.List;
import java.util.UUID;
@Entity
@DiscriminatorValue("authorized_itinerary")
public class AuthorizedItinerary extends Itinerary {
    public AuthorizedItinerary() {
        this.setState(ContentState.UPLOADABLE);
    }
    public AuthorizedItinerary(UUID id, String name, Coordinates coordinates, String description, String author, Date creationDate, Date expiryDate, ContentState state, List<POI> itineraryPois, List<AuthenticatedTourist> tourists, List<Attachment> attachments) {
        super(id, name, coordinates, description, author, creationDate, expiryDate, state, itineraryPois, tourists, attachments);
        this.setState(ContentState.UPLOADABLE);
    }
}
