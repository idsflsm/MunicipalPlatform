package it.unicam.cs.idsflsm.municipalplatform.domain.entities.content;

import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentStatus;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;

import java.util.UUID;

public interface IAttachment {
    UUID getId();
    String getName();
    Date getExpiryDate();
    ContentStatus getState();
    void setName(String name);
    void setExpiryDate(Date expiryDate);
    void setState(ContentStatus state);
}
