package it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.ItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.contest.ContributionDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedTouristDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedUserDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedTourist;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Coordinates;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
public abstract class POIDto {
    private UUID id = UUID.randomUUID();
    private String name;
    private Coordinates coordinates;
    private String description;
    private String author;
    private Date creationDate;
    private Date expiryDate;
    private ContentState state;
    @JsonIgnore
    @JsonBackReference
    private List<ItineraryDto> poiItineraries = new ArrayList<ItineraryDto>();
    @JsonIgnore
    @JsonBackReference
    private List<AuthenticatedUserDto> tourists = new ArrayList<AuthenticatedUserDto>();
    @JsonManagedReference
    private List<AttachmentDto> attachments = new ArrayList<AttachmentDto>();
    @JsonIgnore
    @JsonBackReference
    private ContributionDto contribution;
    public POIDto() {
    }
    public POIDto(String name, Coordinates coordinates, String description, String author, Date creationDate, Date expiryDate, ContentState state, List<ItineraryDto> poiItineraries, List<AuthenticatedUserDto> tourists, List<AttachmentDto> attachments, ContributionDto contribution) {
        this.name = name;
        this.coordinates = coordinates;
        this.description = description;
        this.author = author;
        this.creationDate = creationDate;
        this.expiryDate = expiryDate;
        this.state = state;
        this.poiItineraries = poiItineraries;
        this.tourists = tourists;
        this.attachments = attachments;
        this.contribution = contribution;
    }
    public POIDto allWithState(ContentState state) {
        if (this.state == state) {
            this.setAttachments(this.attachments.stream().filter(attachmentDto -> attachmentDto.getState().equals(ContentState.UPLOADED)).toList());
            return this;
        } else {
            return null;
        }
    }
}
