package it.unicam.cs.idsflsm.municipalplatform.application.builders.content.poi;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.AuthorizedPOI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contribution;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Coordinates;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
@Getter
@Setter
@NoArgsConstructor
public class AuthorizedPOIBuilder extends POIBuilder {
    @Override
    public POI build() {
        return new AuthorizedPOI(
                this.getName(),
                this.getCoordinates(),
                this.getDescription(),
                this.getAuthor(),
                this.getCreationDate(),
                this.getExpiryDate(),
                this.getContentState(),
                this.getPoiItineraries(),
                this.getUsers(),
                this.getAttachments(),
                this.getContribution()
        );
    }
}
