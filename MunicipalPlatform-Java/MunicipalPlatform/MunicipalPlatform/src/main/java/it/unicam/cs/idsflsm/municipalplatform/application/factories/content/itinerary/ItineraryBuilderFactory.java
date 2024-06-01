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
@Component
@AllArgsConstructor
public class ItineraryBuilderFactory {
    private final PendingItineraryBuilder _pendingItineraryBuilder;
    private final AuthorizedItineraryBuilder _authorizedItineraryBuilder;
    public IItineraryBuilder createItineraryBuilder(UserPermission permission) {
        if (permission.equals(UserPermission.CONTRIBUTOR_CONTENT_CREATE_PENDING)) {
            return _pendingItineraryBuilder;
        }
        if (permission.equals(UserPermission.AUTHCONTRIBUTOR_CONTENT_CREATE_AUTHORIZED)) {
            return _authorizedItineraryBuilder;
        }
        return null;
    }
}
