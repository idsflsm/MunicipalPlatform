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
@Component
@Getter
@Setter
@NoArgsConstructor
public abstract class AttachmentBuilder implements IAttachmentBuilder {
    private String name;
    private String description;
    private String author;
    private Date creationDate;
    private Date expiryDate;
    private ContentState state;
    private POI poi;
    private Itinerary itinerary;
    private List<Report> reports = new ArrayList<Report>();
    @Override
    public abstract Attachment build();
}
