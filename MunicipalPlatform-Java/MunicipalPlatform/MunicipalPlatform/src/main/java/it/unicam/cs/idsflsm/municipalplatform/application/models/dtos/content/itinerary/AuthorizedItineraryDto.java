package it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.POIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.contest.ContributionDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedUserDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Coordinates;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;

import java.util.List;
/**
 * Represents a DTO related to the entity AuthorizedItinerary.
 * It contains all fields with simple types
 * and the DTOs of entity fields
 */
public class AuthorizedItineraryDto extends ItineraryDto {
    public AuthorizedItineraryDto() {
        this.setState(ContentState.UPLOADABLE);
    }
    public AuthorizedItineraryDto(String name, Coordinates coordinates, String description, String author, Date creationDate, Date expiryDate, ContentState state, List<POIDto> itineraryPois, List<AuthenticatedUserDto> users, List<AttachmentDto> attachments, ContributionDto contribution) {
        super(name, coordinates, description, author, creationDate, expiryDate, state, itineraryPois, users, attachments, contribution);
    }
}
