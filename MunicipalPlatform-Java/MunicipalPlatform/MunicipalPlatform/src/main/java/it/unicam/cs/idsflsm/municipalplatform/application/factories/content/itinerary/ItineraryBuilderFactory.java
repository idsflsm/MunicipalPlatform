package it.unicam.cs.idsflsm.municipalplatform.application.factories.content.itinerary;
import it.unicam.cs.idsflsm.municipalplatform.application.builders.content.itinerary.AuthorizedItineraryBuilder;
import it.unicam.cs.idsflsm.municipalplatform.application.builders.content.itinerary.IItineraryBuilder;
import it.unicam.cs.idsflsm.municipalplatform.application.builders.content.itinerary.PendingItineraryBuilder;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedUserDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserPermission;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserRole;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
/**
 * Builder factory class for the creation of different types of Itinerary builders
 */
@Component
@AllArgsConstructor
public class ItineraryBuilderFactory {
    /**
     * The builder for creating PendingItinerary instances
     */
    private final PendingItineraryBuilder _pendingItineraryBuilder;
    /**
     * The builder for creating AuthorizedItinerary instances
     */
    private final AuthorizedItineraryBuilder _authorizedItineraryBuilder;
    /**
     * Method for creating an Itinerary builder based on the provided user permission
     * @param permission the user permission determining the type of builder to be created
     * @return an Itinerary builder corresponding to the user permission,
     * if a matching builder is found, null otherwise
     */
    public IItineraryBuilder createItineraryBuilder(UserPermission permission) {
        if (permission.equals(UserPermission.CONTRIBUTOR_CONTENT_CREATE_PENDING)) {
            return _pendingItineraryBuilder;
        }
        if (permission.equals(UserPermission.AUTHCONTRIBUTOR_CONTENT_CREATE_AUTHORIZED)
                || permission.equals(UserPermission.CURATOR_CONTENT_UPDATE)) {
            return _authorizedItineraryBuilder;
        }
        return null;
    }
}
