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
@Component
@AllArgsConstructor
public class POIBuilderFactory {
    private final PendingPOIBuilder _pendingPOIBuilder;
    private final AuthorizedPOIBuilder _authorizedPOIBuilder;
    public IPOIBuilder createPOIBuilder(UserPermission permission) {
        if (permission.equals(UserPermission.CONTRIBUTOR_CONTENT_CREATE_PENDING)) {
            return _pendingPOIBuilder;
        }
        if (permission.equals(UserPermission.AUTHCONTRIBUTOR_CONTENT_CREATE_AUTHORIZED)) {
            return _authorizedPOIBuilder;
        }
        return null;
    }
}
