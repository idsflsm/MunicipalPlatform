package it.unicam.cs.idsflsm.municipalplatform.infrastructure.contexts.itinerary;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Itinerary;

import java.util.List;

public class PendingStateItineraries {
    private final List<Itinerary> itineraries;
    public PendingStateItineraries(List<Itinerary> itineraries) {
        this.itineraries = itineraries;
    }
    public void addToList(Itinerary itinerary) {
        itineraries.add(itinerary);
    }
}
