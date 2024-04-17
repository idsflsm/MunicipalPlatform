package it.unicam.cs.idsflsm.municipalplatform.webapi.controllers.itinerary;

import it.unicam.cs.idsflsm.municipalplatform.application.services.itinerary.IDeleteItineraryService;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Itinerary;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class DeleteItineraryController {
    private final IDeleteItineraryService _deleteItineraryService;
    public DeleteItineraryController(IDeleteItineraryService deleteItineraryService) {
        _deleteItineraryService = deleteItineraryService;
    }
    public List<Itinerary> getAllItineraries() {

    }
    public Itinerary selectItinerary(UUID id) {

    }
    public boolean compare(Date expiryDate, Date currentInstant) {

    }
}
