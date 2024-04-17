package it.unicam.cs.idsflsm.municipalplatform.application.services.itinerary;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentStatus;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.contexts.itinerary.DeletingStateItineraries;

import java.util.List;
import java.util.UUID;

public class DeleteItineraryService implements IDeleteItineraryService {
    private final DeletingStateItineraries _deletingStateItineraries;
    public DeleteItineraryService(DeletingStateItineraries deletingStateItineraries) {
        _deletingStateItineraries = deletingStateItineraries;
    }
    @Override
    public List<Itinerary> getAllItineraries() {
        return List.of();
    }
    @Override
    public Itinerary selectItinerary(UUID id) {
        return null;
    }
    @Override
    public void setState(ContentStatus state) {

    }
}
