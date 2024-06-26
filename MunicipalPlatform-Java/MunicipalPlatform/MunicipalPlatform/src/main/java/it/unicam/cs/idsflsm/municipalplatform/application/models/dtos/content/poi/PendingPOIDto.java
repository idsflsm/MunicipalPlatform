package it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.ItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.contest.ContributionDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedUserDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Coordinates;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;

import java.util.List;
/**
 * Represents a DTO related to the entity PendingPOI.
 * It contains all fields with simple types
 * and the DTOs of entity fields
 */
public class PendingPOIDto extends POIDto {
    public PendingPOIDto() {
        this.setState(ContentState.VALIDABLE);
    }
    public PendingPOIDto(String name, Coordinates coordinates, String description, String author, Date creationDate, Date expiryDate, ContentState state, List<ItineraryDto> poiItineraries, List<AuthenticatedUserDto> tourists, List<AttachmentDto> attachments, ContributionDto contribution) {
        super(name, coordinates, description, author, creationDate, expiryDate, state, poiItineraries, tourists, attachments, contribution);
    }
}
