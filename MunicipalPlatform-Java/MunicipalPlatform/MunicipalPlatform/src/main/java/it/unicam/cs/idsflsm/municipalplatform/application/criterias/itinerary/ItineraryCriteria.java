package it.unicam.cs.idsflsm.municipalplatform.application.criterias.itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.AuthorizedItinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.PendingItinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;

import java.util.function.Predicate;
public class ItineraryCriteria {
    public static Predicate<Itinerary> isPendingItinerary() {
        return itinerary -> itinerary.getClass() == PendingItinerary.class;
    }
    public static Predicate<Itinerary> isAuthorizedItinerary() {
        return itinerary -> itinerary.getClass() == AuthorizedItinerary.class;
    }
    public static Predicate<Itinerary> isInDisposableState() {
        return itinerary -> itinerary.getState().equals(ContentState.DISPOSABLE);
    }
    public static Predicate<Itinerary> isInValidableState() {
        return itinerary -> itinerary.getState().equals(ContentState.VALIDABLE);
    }
    public static Predicate<Itinerary> isInUploadableState() {
        return itinerary -> itinerary.getState().equals(ContentState.UPLOADABLE);
    }
    public static Predicate<Itinerary> isInUploadedState() {
        return itinerary -> itinerary.getState().equals(ContentState.UPLOADED);
    }
}
