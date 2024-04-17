package it.unicam.cs.idsflsm.municipalplatform.application.services.POI;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.PendingPOI;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentStatus;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Coordinates;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.contexts.POI.DeletingStatePOIs;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.contexts.POI.PendingStatePOIs;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.contexts.POI.ReadyStatePOIs;

import java.util.List;
import java.util.UUID;

public class PendingPOIService implements IPendingPOIService {
    private final PendingStatePOIs _pendingStatePOIs;
    private final ReadyStatePOIs _readyStatePOIs;
    private final DeletingStatePOIs _deletingStatePOIs;
    public PendingPOIService(PendingStatePOIs pendingStatePOIs, ReadyStatePOIs readyStatePOIs, DeletingStatePOIs deletingStatePOIs) {
        _pendingStatePOIs = pendingStatePOIs;
        _readyStatePOIs = readyStatePOIs;
        _deletingStatePOIs = deletingStatePOIs;
    }
    @Override
    public void addPOI(String name, Coordinates coordinates) {

    }
    @Override
    public List<PendingPOI> getAllPendingPOIs() {
        return List.of();
    }
    @Override
    public POI selectPOI(UUID id) {
        return null;
    }
    @Override
    public void setState(ContentStatus state) {

    }
}
