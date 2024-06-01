package it.unicam.cs.idsflsm.municipalplatform.application.builders.content.itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contribution;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Coordinates;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;

import java.util.List;
public interface IItineraryBuilder {
    void setName(String name);
    void setCoordinates(Coordinates coordinates);
    void setDescription(String description);
    void setAuthor(String author);
    void setCreationDate(Date creationDate);
    void setExpiryDate(Date expiryDate);
    void setContentState(ContentState contentState);
    void setItineraryPois(List<POI> itineraryPois);
    void setUsers(List<AuthenticatedUser> users);
    void setAttachments(List<Attachment> attachments);
    void setContribution(Contribution contribution);
    Itinerary build();
}
