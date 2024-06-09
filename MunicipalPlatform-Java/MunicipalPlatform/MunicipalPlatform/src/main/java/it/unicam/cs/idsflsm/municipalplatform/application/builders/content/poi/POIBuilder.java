package it.unicam.cs.idsflsm.municipalplatform.application.builders.content.poi;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
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
/**
 * General builder class for POI builders
 */
@Component
@Getter
@Setter
@NoArgsConstructor
public abstract class POIBuilder implements IPOIBuilder {
    /**
     * The name of the POI
     */
    private String name;
    /**
     * The coordinates of the POI
     */
    private Coordinates coordinates;
    /**
     * The description of the POI
     */
    private String description;
    /**
     * The author of the POI
     */
    private String author;
    /**
     * The creation date of the POI
     */
    private Date creationDate;
    /**
     * The expiry date of the POI
     */
    private Date expiryDate;
    /**
     * The state of the POI
     */
    private ContentState contentState;
    /**
     * The list of itineraries that contain the poi
     */
    private List<Itinerary> poiItineraries = new ArrayList<>();
    /**
     * The list of authenticated users that saved the poi
     */
    private List<AuthenticatedUser> users = new ArrayList<>();
    /**
     * The list of attachments associated to the poi
     */
    private List<Attachment> attachments = new ArrayList<>();
    /**
     * The corresponding contest contribution (if exists)
     */
    private Contribution contribution;
    @Override
    public abstract POI build();
}
