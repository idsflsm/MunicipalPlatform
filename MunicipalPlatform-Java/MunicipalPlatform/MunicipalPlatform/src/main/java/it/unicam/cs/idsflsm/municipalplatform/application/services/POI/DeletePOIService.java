package it.unicam.cs.idsflsm.municipalplatform.application.services.POI;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentStatus;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.contexts.POI.DeletingStatePOIs;

import java.util.List;
import java.util.UUID;

public class DeletePOIService implements IDeletePOIService {
    private final DeletingStatePOIs _deletingStatePOIs;
    public DeletePOIService(DeletingStatePOIs deletingStatePOIs) {
        _deletingStatePOIs = deletingStatePOIs;
    }
    @Override
    public List<POI> getAllPOIs() {
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
