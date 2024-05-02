package it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Content;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.report.Report;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.List;
import java.util.UUID;
@Entity
@DiscriminatorValue("pending_attachment")
public class PendingAttachment extends Attachment {
    public PendingAttachment() {
    }
    public PendingAttachment(UUID id, String name, String description, String author, Date creationDate, Date expiryDate, ContentState state, POI poi, Itinerary itinerary, List<Report> reports) {
        super(id, name, description, author, creationDate, expiryDate, state, poi, itinerary, reports);
    }
}
