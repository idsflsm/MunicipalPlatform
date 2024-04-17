package it.unicam.cs.idsflsm.municipalplatform.webapi.controllers.POI;

import it.unicam.cs.idsflsm.municipalplatform.application.services.POI.IAuthorizedPOIService;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Coordinates;

public class AuthorizedPOIController {
    private final IAuthorizedPOIService _authorizedPOIService;
    public AuthorizedPOIController(IAuthorizedPOIService authorizedPOIService) {
        _authorizedPOIService = authorizedPOIService;
    }
    public boolean confirm() {

    }
    public void addPOI(String name, Coordinates coordinates) {

    }
}
