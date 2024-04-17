package it.unicam.cs.idsflsm.municipalplatform.application.services.itinerary;

import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Coordinates;

public interface IAuthorizedItineraryService {
    void addItinerary(String name, Coordinates coordinates);
}
