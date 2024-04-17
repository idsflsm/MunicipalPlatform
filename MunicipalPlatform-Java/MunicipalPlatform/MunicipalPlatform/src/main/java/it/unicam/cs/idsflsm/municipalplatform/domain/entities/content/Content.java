package it.unicam.cs.idsflsm.municipalplatform.domain.entities.content;

import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentStatus;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Coordinates;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;

import java.util.UUID;

public abstract class Content {
    protected final UUID id = UUID.randomUUID();
    protected String name;
    protected Coordinates coordinates;
    protected Date expiryDate;
    protected ContentStatus state;
}
