package it.unicam.cs.idsflsm.municipalplatform.application.services.POI;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentStatus;

import java.util.List;
import java.util.UUID;

public interface IDeletePOIService {
    List<POI> getAllPOIs();
    POI selectPOI(UUID id);
    void setState(ContentStatus state);
}
