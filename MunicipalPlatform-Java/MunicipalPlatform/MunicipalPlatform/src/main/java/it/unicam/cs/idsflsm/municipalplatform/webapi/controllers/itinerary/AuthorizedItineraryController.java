package it.unicam.cs.idsflsm.municipalplatform.webapi.controllers.itinerary;

import it.unicam.cs.idsflsm.municipalplatform.application.services.itinerary.IAuthorizedItineraryService;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Coordinates;

public class AuthorizedItineraryController {
    private final IAuthorizedItineraryService _authorizedItineraryService;
    public AuthorizedItineraryController(IAuthorizedItineraryService authorizedItineraryService) {
        _authorizedItineraryService = authorizedItineraryService;
    }
    public boolean confirm() {

    }
    public void addItinerary(String name, Coordinates coordinates) {

    }
}
