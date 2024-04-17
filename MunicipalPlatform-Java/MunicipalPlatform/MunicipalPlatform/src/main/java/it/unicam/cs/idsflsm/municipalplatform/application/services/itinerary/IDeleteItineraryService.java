package it.unicam.cs.idsflsm.municipalplatform.application.services.itinerary;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentStatus;

import java.util.List;
import java.util.UUID;

public interface IDeleteItineraryService {
    List<Itinerary> getAllItineraries();
    Itinerary selectItinerary(UUID id);
    void setState(ContentStatus state);
}
