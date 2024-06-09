package it.unicam.cs.idsflsm.municipalplatform.application.builders.attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.report.Report;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
/**
 * General builder class for Attachment builders
 */
@Component
@Getter
@Setter
@NoArgsConstructor
public abstract class AttachmentBuilder implements IAttachmentBuilder {
    /**
     * The name of the attachment
     */
    private String name;
    /**
     * The description of the attachment
     */
    private String description;
    /**
     * The author of the attachment
     */
    private String author;
    /**
     * The creation date of the attachment
     */
    private Date creationDate;
    /**
     * The expiry date of the attachment
     */
    private Date expiryDate;
    /**
     * The state of the attachment
     */
    private ContentState state;
    /**
     * The POI associated to the attachment (if exists)
     */
    private POI poi;
    /**
     * The Itinerary associated to the attachment (if exists)
     */
    private Itinerary itinerary;
    /**
     * The list of active reports of the attachment
     */
    private List<Report> reports = new ArrayList<Report>();
    @Override
    public abstract Attachment build();
}
