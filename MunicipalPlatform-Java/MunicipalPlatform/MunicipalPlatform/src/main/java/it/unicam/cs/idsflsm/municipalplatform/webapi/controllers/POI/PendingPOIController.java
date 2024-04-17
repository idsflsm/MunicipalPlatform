package it.unicam.cs.idsflsm.municipalplatform.webapi.controllers.POI;

import it.unicam.cs.idsflsm.municipalplatform.application.services.POI.IPendingPOIService;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.PendingPOI;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Coordinates;

import java.util.List;
import java.util.UUID;

public class PendingPOIController {
    private final IPendingPOIService _pendingPOIService;
    public PendingPOIController(IPendingPOIService pendingPOIService) {
        _pendingPOIService = pendingPOIService;
    }
    public boolean confirm() {

    }
    public void addPOI(String name, Coordinates coordinates) {

    }
    public List<PendingPOI> getAllPendingPOIs() {

    }
    public PendingPOI selectPOI(UUID id) {

    }
    public boolean validate(PendingPOI poi) {

    }
}
