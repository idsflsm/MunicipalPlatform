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
/**
 * Represents a DTO related to the entity AuthorizedAttachment.
 * It contains all fields with simple types
 * and the DTOs of entity fields
 */
public class AuthorizedAttachmentDto extends AttachmentDto {
    public AuthorizedAttachmentDto() {
        this.setState(ContentState.UPLOADABLE);
    }
    public AuthorizedAttachmentDto(String name, String description, String author, Date creationDate, Date expiryDate, ContentState state, POIDto poi, ItineraryDto itinerary, List<ReportDto> reports) {
        super(name, description, author, creationDate, expiryDate, state, poi, itinerary, reports);
    }
}
