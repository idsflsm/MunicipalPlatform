package it.unicam.cs.idsflsm.municipalplatform.application.criterias.poi;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.AuthorizedPOI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.PendingPOI;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;

import java.util.function.Predicate;

public class POICriteria<T extends POI> {
    public static Predicate<POI> isPendingPOI() {
        return poi -> poi.getClass() == PendingPOI.class;
    }
    public static Predicate<POI> isAuthorizedPOI() {
        return poi -> poi.getClass() == AuthorizedPOI.class;
    }
    public static Predicate<POI> isInDisposableState() {
        return poi -> poi.getState().equals(ContentState.DISPOSABLE);
    }
    public static Predicate<POI> isInValidableState() {
        return poi -> poi.getState().equals(ContentState.VALIDABLE);
    }
    public static Predicate<POI> isInUploadableState() {
        return poi -> poi.getState().equals(ContentState.UPLOADABLE);
    }
    public static Predicate<POI> isInUploadedState() {
        return poi -> poi.getState().equals(ContentState.UPLOADED);
    }
}
