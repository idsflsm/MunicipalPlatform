package it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.report.Report;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.List;
import java.util.UUID;
@Entity
@DiscriminatorValue("authorized_attachment")
public class AuthorizedAttachment extends Attachment {
    public AuthorizedAttachment() {
        this.setState(ContentState.UPLOADABLE);
    }
    public AuthorizedAttachment(String name, String description, String author, Date creationDate, Date expiryDate, ContentState state, POI poi, Itinerary itinerary, List<Report> reports) {
        super(name, description, author, creationDate, expiryDate, state, poi, itinerary, reports);
    }
}
