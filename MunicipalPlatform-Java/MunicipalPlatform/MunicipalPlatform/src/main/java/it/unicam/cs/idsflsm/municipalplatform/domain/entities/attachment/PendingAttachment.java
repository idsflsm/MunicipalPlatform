package it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.report.Report;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.List;
/**
 * Represents an attachment on the platform, acting as PendingAttachment
 */
@Entity
@DiscriminatorValue("pending_attachment")
public class PendingAttachment extends Attachment {
    public PendingAttachment() {
        this.setState(ContentState.VALIDABLE);
    }
    public PendingAttachment(String name, String description, String author, Date creationDate, Date expiryDate, ContentState state, POI poi, Itinerary itinerary, List<Report> reports) {
        super(name, description, author, creationDate, expiryDate, state, poi, itinerary, reports);
    }
}
