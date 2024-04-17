package it.unicam.cs.idsflsm.municipalplatform.application.services.POI;

import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Coordinates;

public interface IAuthorizedPOIService {
    void addPOI(String name, Coordinates coordinates);
}
