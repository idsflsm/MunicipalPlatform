package it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.ItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.POIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.report.ReportDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;

import java.util.List;
/**
 * Represents a DTO related to the entity PendingAttachment.
 * It contains all fields with simple types
 * and the DTOs of entity fields
 */
public class PendingAttachmentDto extends AttachmentDto {
    public PendingAttachmentDto() {
        this.setState(ContentState.VALIDABLE);
    }
    public PendingAttachmentDto(String name, String description, String author, Date creationDate, Date expiryDate, ContentState state, POIDto poi, ItineraryDto itinerary, List<ReportDto> reports) {
        super(name, description, author, creationDate, expiryDate, state, poi, itinerary, reports);
    }
}
