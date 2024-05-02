package it.unicam.cs.idsflsm.municipalplatform.domain.entities.content;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Coordinates;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;

import java.util.List;
import java.util.UUID;

public interface IContent {
    UUID getId();
    void setId(UUID id);
    String getName();
    void setName(String name);
    Coordinates getCoordinates();
    void setCoordinates(Coordinates coordinates);
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
}
