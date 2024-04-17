package it.unicam.cs.idsflsm.municipalplatform.application.services.itinerary;

import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Coordinates;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.contexts.itinerary.ReadyStateItineraries;

public class AuthorizedItineraryService implements IAuthorizedItineraryService {
    private final ReadyStateItineraries _readyStateItineraries;
    public AuthorizedItineraryService(ReadyStateItineraries readyStateItineraries) {
        _readyStateItineraries = readyStateItineraries;
    }
    @Override
    public void addItinerary(String name, Coordinates coordinates) {

    }
}
