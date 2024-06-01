package it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.ItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.POIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.report.ReportDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.report.Report;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;

import java.util.List;
import java.util.UUID;
public class PendingAttachmentDto extends AttachmentDto {
    public PendingAttachmentDto() {
        this.setState(ContentState.VALIDABLE);
    }
    public PendingAttachmentDto(String name, String description, String author, Date creationDate, Date expiryDate, ContentState state, POIDto poi, ItineraryDto itinerary, List<ReportDto> reports) {
        super(name, description, author, creationDate, expiryDate, state, poi, itinerary, reports);
    }
}
