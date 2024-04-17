package it.unicam.cs.idsflsm.municipalplatform.webapi.controllers.POI;

import it.unicam.cs.idsflsm.municipalplatform.application.services.POI.IDeletePOIService;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.POI;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class DeletePOIController {
    private final IDeletePOIService _deletePOIService;
    public DeletePOIController(IDeletePOIService deletePOIService) {
        _deletePOIService = deletePOIService;
    }
    public List<POI> getAllPOIs() {

    }
    public POI selectPOI(UUID id) {

    }
    public boolean compare(Date expiryDate, Date currentInstant) {

    }
}
