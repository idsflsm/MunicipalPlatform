package it.unicam.cs.idsflsm.municipalplatform.application.builders.attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.report.Report;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;

import java.util.List;
/**
 * Interface for building Attachment instances
 */
public interface IAttachmentBuilder {
    void setName(String name);
    void setDescription(String description);
    void setAuthor(String author);
    void setCreationDate(Date creationDate);
    void setExpiryDate(Date expiryDate);
    void setState(ContentState state);
    void setPoi(POI poi);
    void setItinerary(Itinerary itinerary);
    void setReports(List<Report> reports);
    /**
     * Builds and returns an Attachment instance based on the stored information
     *
     * @return the built Attachment instance
     */
    Attachment build();
}
