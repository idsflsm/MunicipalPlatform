package it.unicam.cs.idsflsm.municipalplatform.application.services.POI;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.PendingPOI;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentStatus;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Coordinates;

import java.util.List;
import java.util.UUID;

public interface IPendingPOIService {
    void addPOI(String name, Coordinates coordinates);
    List<PendingPOI> getAllPendingPOIs();
    POI selectPOI(UUID id);
    void setState(ContentStatus state);
}
