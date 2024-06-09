package it.unicam.cs.idsflsm.municipalplatform.application.criterias.content.poi;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.AuthorizedPOI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.PendingPOI;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Coordinates;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;

import java.util.UUID;
import java.util.function.Predicate;
/**
 * Utility class providing various predicates for filtering POI objects based on different criteria.
 * In general, predicates on entity fields are optional filters
 */
public class POICriteria<T extends POI> {
    public static Predicate<POI> isPendingPoi() {
        return poi -> poi.getClass() == PendingPOI.class;
    }
    public static Predicate<POI> isAuthorizedPoi() {
        return poi -> poi.getClass() == AuthorizedPOI.class;
    }
    public static Predicate<POI> isInRemovableState() {
        return poi -> poi.getState().equals(ContentState.REMOVABLE);
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
    public static Predicate<POI> hasId(UUID id) {
        if (id != null) {
            return poi -> poi.getId().equals(id);
        } else {
            return poi -> true;
        }
    }
    public static Predicate<POI> hasName(String name) {
        if (name != null && !name.isBlank()) {
            return poi -> poi.getName().toLowerCase().contains(name.toLowerCase());
        } else {
            return poi -> true;
        }
    }
    public static Predicate<POI> hasCoordinates(Coordinates coordinates) {
        if (coordinates != null) {
            return poi -> poi.getCoordinates().equals(coordinates);
        } else {
            return poi -> true;
        }
    }
    public static Predicate<POI> hasDescription(String description) {
        if (description != null && !description.isBlank()) {
            return poi -> poi.getDescription().toLowerCase().contains(description.toLowerCase());
        } else {
            // No filtering if description is blank
            return poi -> true;
        }
    }
    public static Predicate<POI> hasAuthor(String author) {
        if (author != null && !author.isBlank()) {
            return poi -> poi.getAuthor().toLowerCase().contains(author.toLowerCase());
        } else {
            // No filtering if author is blank
            return poi -> true;
        }
    }
    public static Predicate<POI> hasCreationDate(Date creationDate) {
        if (creationDate != null) {
            return poi -> poi.getCreationDate().equals(creationDate);
        } else {
            // No filtering if creationDate is null
            return poi -> true;
        }
    }
    public static Predicate<POI> hasExpiryDate(Date expiryDate) {
        if (expiryDate != null) {
            return poi -> poi.getExpiryDate().equals(expiryDate);
        } else {
            // No filtering if expiryDate is null
            return poi -> true;
        }
    }
    public static Predicate<POI> hasState(ContentState state) {
        if (state != null) {
            return poi -> poi.getState().equals(state);
        } else {
            // No filtering if state is null
            return poi -> true;
        }
    }
    @SafeVarargs
    public static Predicate<POI> criteriaBuilder(Predicate<POI>... predicates) {
        Predicate<POI> result = poi -> true;
        for (Predicate<POI> predicate : predicates) {
            result = result.and(predicate);
        }
        return result;
    }
}