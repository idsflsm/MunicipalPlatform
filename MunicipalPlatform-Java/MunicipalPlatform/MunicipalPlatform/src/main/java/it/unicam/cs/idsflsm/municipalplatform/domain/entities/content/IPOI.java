package it.unicam.cs.idsflsm.municipalplatform.domain.entities.content;

import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentStatus;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Coordinates;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;

import java.util.UUID;

public interface IPOI {
    UUID getId();
    String getName();
    Coordinates getCoordinates();
    Date getExpiryDate();
    ContentStatus getState();
    void setName(String name);
    void setCoordinates(Coordinates coordinates);
    void setExpiryDate(Date expiryDate);
    void setState(ContentStatus state);
}
