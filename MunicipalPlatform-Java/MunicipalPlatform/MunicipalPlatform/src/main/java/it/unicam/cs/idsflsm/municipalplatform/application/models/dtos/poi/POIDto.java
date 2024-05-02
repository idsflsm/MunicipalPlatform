package it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.poi;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedTourist;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Coordinates;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class POIDto {
    private UUID id;
    private String name;
    private Coordinates coordinates;
    private String description;
    private String author;
    private Date creationDate;
    private Date expiryDate;
    private ContentState state;
    private List<Itinerary> poiItineraries;
    private List<AuthenticatedTourist> tourists;
    private List<Attachment> attachments;
}