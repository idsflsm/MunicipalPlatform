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
@Component
@Getter
@Setter
@NoArgsConstructor
public abstract class POIBuilder implements IPOIBuilder {
    private String name;
    private Coordinates coordinates;
    private String description;
    private String author;
    private Date creationDate;
    private Date expiryDate;
    private ContentState contentState;
    private List<Itinerary> poiItineraries = new ArrayList<Itinerary>();
    private List<AuthenticatedUser> users = new ArrayList<AuthenticatedUser>();
    private List<Attachment> attachments = new ArrayList<Attachment>();
    private Contribution contribution;
    @Override
    public abstract POI build();
}
