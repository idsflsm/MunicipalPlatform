package it.unicam.cs.idsflsm.municipalplatform.application.criterias.content.itinerary;
import it.unicam.cs.idsflsm.municipalplatform.application.criterias.content.poi.POICriteria;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.AuthorizedItinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.PendingItinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Coordinates;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;

import java.util.UUID;
import java.util.function.Predicate;
public class ItineraryCriteria {
    public static Predicate<Itinerary> isPendingItinerary() {
        return itinerary -> itinerary.getClass() == PendingItinerary.class;
    }
    public static Predicate<Itinerary> isAuthorizedItinerary() {
        return itinerary -> itinerary.getClass() == AuthorizedItinerary.class;
    }
    public static Predicate<Itinerary> isInRemovableState() {
        return itinerary -> itinerary.getState().equals(ContentState.REMOVABLE);
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
    public static Predicate<Itinerary> hasId(UUID id) {
        if (id != null) {
            return itinerary -> itinerary.getId().equals(id);
        } else {
            return itinerary -> true;
        }
    }
    public static Predicate<Itinerary> hasName(String name) {
        if (name != null && !name.isBlank()) {
            return itinerary -> itinerary.getName().toLowerCase().contains(name.toLowerCase());
        } else {
            return itinerary -> true;
        }
    }
    public static Predicate<Itinerary> hasCoordinates(Coordinates coordinates) {
        if (coordinates != null) {
            return itinerary -> itinerary.getCoordinates().equals(coordinates);
        } else {
            return itinerary -> true;
        }
    }
    public static Predicate<Itinerary> hasDescription(String description) {
        if (description != null && !description.isBlank()) {
            return attachment -> attachment.getDescription().toLowerCase().contains(description.toLowerCase());
        } else {
            // TODO : (NOT TODO) NO FILTERING IF NAME ISBLANK
            return attachment -> true;
        }
    }
    public static Predicate<Itinerary> hasAuthor(String author) {
        if (author != null && !author.isBlank()) {
            return attachment -> attachment.getAuthor().toLowerCase().contains(author.toLowerCase());
        } else {
            // TODO : (NOT TODO) NO FILTERING IF NAME ISBLANK
            return attachment -> true;
        }
    }
    public static Predicate<Itinerary> hasCreationDate(Date creationDate) {
        if (creationDate != null) {
            return attachment -> attachment.getCreationDate().equals(creationDate);
        } else {
            // TODO : (NOT TODO) NO FILTERING IF NAME ISBLANK
            return attachment -> true;
        }
    }
    public static Predicate<Itinerary> hasExpiryDate(Date expiryDate) {
        if (expiryDate != null) {
            return attachment -> attachment.getExpiryDate().equals(expiryDate);
        } else {
            // TODO : (NOT TODO) NO FILTERING IF NAME ISBLANK
            return attachment -> true;
        }
    }
    public static Predicate<Itinerary> hasState(ContentState state) {
        if (state != null) {
            return attachment -> attachment.getState().equals(state);
        } else {
            // TODO : (NOT TODO) NO FILTERING IF NAME ISBLANK
            return attachment -> true;
        }
    }
    @SafeVarargs
    public static Predicate<Itinerary> criteriaBuilder(Predicate<Itinerary>... predicates) {
        Predicate<Itinerary> result = attachment -> true;
        for (Predicate<Itinerary> predicate : predicates) {
            result = result.and(predicate);
        }
        return result;
    }
}
