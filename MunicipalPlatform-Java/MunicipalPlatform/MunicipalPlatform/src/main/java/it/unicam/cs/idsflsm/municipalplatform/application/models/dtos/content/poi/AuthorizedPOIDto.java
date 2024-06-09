package it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.ItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.contest.ContributionDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedTouristDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedUserDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedTourist;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Coordinates;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;

import java.util.List;
import java.util.UUID;
/**
 * Represents a DTO related to the entity AuthorizedPOI.
 * It contains all fields with simple types
 * and the DTOs of entity fields
 */
public class AuthorizedPOIDto extends POIDto {
    public AuthorizedPOIDto() {
        this.setState(ContentState.UPLOADABLE);
    }
    public AuthorizedPOIDto(String name, Coordinates coordinates, String description, String author, Date creationDate, Date expiryDate, ContentState state, List<ItineraryDto> poiItineraries, List<AuthenticatedUserDto> tourists, List<AttachmentDto> attachments, ContributionDto contribution) {
        super(name, coordinates, description, author, creationDate, expiryDate, state, poiItineraries, tourists, attachments, contribution);
    }
}
