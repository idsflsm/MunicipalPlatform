package it.unicam.cs.idsflsm.municipalplatform.application.services.POI;

import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Coordinates;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.contexts.POI.ReadyStatePOIs;

public class AuthorizedPOIService implements IAuthorizedPOIService {
    private final ReadyStatePOIs _readyStatePOIs;
    public AuthorizedPOIService(ReadyStatePOIs readyStatePOIs) {
        _readyStatePOIs = readyStatePOIs;
    }
    @Override
    public void addPOI(String name, Coordinates coordinates) {

    }
}
