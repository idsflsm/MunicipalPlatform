package it.unicam.cs.idsflsm.municipalplatform.application.factories.content.poi;
import it.unicam.cs.idsflsm.municipalplatform.application.builders.content.poi.AuthorizedPOIBuilder;
import it.unicam.cs.idsflsm.municipalplatform.application.builders.content.poi.IPOIBuilder;
import it.unicam.cs.idsflsm.municipalplatform.application.builders.content.poi.PendingPOIBuilder;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedUserDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserPermission;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserRole;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
/**
 * Builder factory class for the creation of different types of POI builders
 */
@Component
@AllArgsConstructor
public class POIBuilderFactory {
    /**
     * The builder for creating PendingPOI instances
     */
    private final PendingPOIBuilder _pendingPOIBuilder;
    /**
     * The builder for creating AuthorizedPOI instances
     */
    private final AuthorizedPOIBuilder _authorizedPOIBuilder;
    /**
     * Method for creating a POI builder based on the provided user permission
     * @param permission the user permission determining the type of builder to be created
     * @return a POI builder corresponding to the user permission,
     * if a matching builder is found, null otherwise
     */
    public IPOIBuilder createPOIBuilder(UserPermission permission) {
        if (permission.equals(UserPermission.CONTRIBUTOR_CONTENT_CREATE_PENDING)) {
            return _pendingPOIBuilder;
        }
        if (permission.equals(UserPermission.AUTHCONTRIBUTOR_CONTENT_CREATE_AUTHORIZED)
                || permission.equals(UserPermission.CURATOR_CONTENT_UPDATE)) {
            return _authorizedPOIBuilder;
        }
        return null;
    }
}
