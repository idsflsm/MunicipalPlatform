package it.unicam.cs.idsflsm.municipalplatform.infrastructure.contexts.POI;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.POI;

import java.util.List;

public class DeletingStatePOIs {
    private final List<POI> POIs;
    public DeletingStatePOIs(List<POI> poIs) {
        POIs = poIs;
    }
    public void addToList(POI poi) {
        POIs.add(poi);
    }
}
