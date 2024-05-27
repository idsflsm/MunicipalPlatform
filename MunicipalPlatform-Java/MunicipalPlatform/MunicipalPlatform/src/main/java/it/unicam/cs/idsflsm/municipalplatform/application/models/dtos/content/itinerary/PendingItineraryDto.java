package it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.POIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.contest.ContributionDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedTouristDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedUserDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedTourist;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Coordinates;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;

import java.util.List;
import java.util.UUID;
public class PendingItineraryDto extends ItineraryDto {
    public PendingItineraryDto() {
        this.setState(ContentState.VALIDABLE);
    }

    public PendingItineraryDto(UUID id, String name, Coordinates coordinates, String description, String author, Date creationDate, Date expiryDate, ContentState state, List<POIDto> itineraryPois, List<AuthenticatedUserDto> users, List<AttachmentDto> attachments, ContributionDto contribution) {
        super(id, name, coordinates, description, author, creationDate, expiryDate, state, itineraryPois, users, attachments, contribution);
    }
}
