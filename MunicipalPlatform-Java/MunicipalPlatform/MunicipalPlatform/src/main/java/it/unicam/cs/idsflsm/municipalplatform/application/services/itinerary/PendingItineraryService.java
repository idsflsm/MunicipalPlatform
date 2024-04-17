package it.unicam.cs.idsflsm.municipalplatform.application.services.itinerary;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.PendingItinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentStatus;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Coordinates;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.contexts.itinerary.DeletingStateItineraries;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.contexts.itinerary.PendingStateItineraries;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.contexts.itinerary.ReadyStateItineraries;

import java.util.List;
import java.util.UUID;

public class PendingItineraryService implements IPendingItineraryService {
    private final PendingStateItineraries _pendingStateItineraries;
    private final ReadyStateItineraries _readyStateItineraries;
    private final DeletingStateItineraries _deletingStateItineraries;
    public PendingItineraryService(PendingStateItineraries pendingStateItineraries, ReadyStateItineraries readyStateItineraries, DeletingStateItineraries deletingStateItineraries) {
        this._pendingStateItineraries = pendingStateItineraries;
        this._readyStateItineraries = readyStateItineraries;
        this._deletingStateItineraries = deletingStateItineraries;
    }
    @Override
    public void addItinerary(String name, Coordinates coordinates) {

    }
    @Override
    public List<PendingItinerary> getAllPendingItineraries() {
        return List.of();
    }
    @Override
    public PendingItinerary selectItinerary(UUID id) {
        return null;
    }
    @Override
    public void setState(ContentStatus state) {

    }
}
