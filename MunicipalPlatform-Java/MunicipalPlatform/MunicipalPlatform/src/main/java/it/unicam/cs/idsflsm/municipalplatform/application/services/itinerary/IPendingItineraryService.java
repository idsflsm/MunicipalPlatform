package it.unicam.cs.idsflsm.municipalplatform.application.services.itinerary;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.PendingItinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentStatus;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Coordinates;

import java.util.List;
import java.util.UUID;

public interface IPendingItineraryService {
    void addItinerary(String name, Coordinates coordinates);
    List<PendingItinerary> getAllPendingItineraries();
    PendingItinerary selectItinerary(UUID id);
    void setState(ContentStatus state);
}
