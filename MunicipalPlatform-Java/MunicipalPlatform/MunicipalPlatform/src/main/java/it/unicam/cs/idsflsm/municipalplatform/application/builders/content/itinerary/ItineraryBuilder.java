package it.unicam.cs.idsflsm.municipalplatform.application.builders.content.itinerary;
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
 * General builder class for Itinerary builders
 */
@Component
@Getter
@Setter
@NoArgsConstructor
public abstract class ItineraryBuilder implements IItineraryBuilder {
    /**
     * The name of the itinerary
     */
    private String name;
    /**
     * The coordinates of the itinerary
     */
    private Coordinates coordinates;
    /**
     * The description of the itinerary
     */
    private String description;
    /**
     * The author of the itinerary
     */
    private String author;
    /**
     * The creation date of the itinerary
     */
    private Date creationDate;
    /**
     * The expiry date of the itinerary
     */
    private Date expiryDate;
    /**
     * The state of the itinerary
     */
    private ContentState contentState;
    /**
     * The list of POIs associated to the itinerary
     */
    private List<POI> itineraryPois = new ArrayList<>();
    /**
     * The list of authenticated users that saved the itinerary
     */
    private List<AuthenticatedUser> users = new ArrayList<>();
    /**
     * The list of attachments associated to the itinerary
     */
    private List<Attachment> attachments = new ArrayList<>();
    /**
     * The corresponding contest contribution (if exists)
     */
    private Contribution contribution;
    @Override
    public abstract Itinerary build();
}

