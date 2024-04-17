package it.unicam.cs.idsflsm.municipalplatform.webapi.controllers.itinerary;

import it.unicam.cs.idsflsm.municipalplatform.application.services.itinerary.IPendingItineraryService;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.PendingItinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Coordinates;

import java.util.List;
import java.util.UUID;

public class PendingItineraryController {
    private final IPendingItineraryService _pendingItineraryService;
    public PendingItineraryController(IPendingItineraryService pendingItineraryService) {
        _pendingItineraryService = pendingItineraryService;
    }
    public boolean confirm() {

    }
    public void addItinerary(String name, Coordinates coordinates) {

    }
    public List<PendingItinerary> getAllPendingItineraries() {

    }
    public PendingItinerary selectItinerary(UUID id) {

    }
    public boolean validate(PendingItinerary itinerary) {

    }
}
