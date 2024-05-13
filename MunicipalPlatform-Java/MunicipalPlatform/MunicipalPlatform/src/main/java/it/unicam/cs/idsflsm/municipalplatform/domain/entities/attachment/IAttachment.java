package it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;

import java.util.UUID;

public interface IAttachment {
    UUID getId();
    void setId(UUID id);
    String getName();
    void setName(String name);
    String getDescription();
    void setDescription(String description);
    String getAuthor();
    void setAuthor(String author);
    Date getCreationDate();
    void setCreationDate(Date creationDate);
    Date getExpiryDate();
    void setExpiryDate(Date expiryDate);
    ContentState getState();
    void setState(ContentState state);
    POI getPoi();
    void setPoi(POI poi);
    Itinerary getItinerary();
    void setItinerary(Itinerary itinerary);
}
