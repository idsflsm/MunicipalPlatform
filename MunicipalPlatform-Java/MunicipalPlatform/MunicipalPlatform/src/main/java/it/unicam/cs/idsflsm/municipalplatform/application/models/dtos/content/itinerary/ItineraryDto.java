package it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.POIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.contest.ContributionDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedTouristDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedUserDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
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
/**
 * Represents a DTO related to the entity Itinerary.
 * It contains all fields with simple types
 * and the DTOs of entity fields
 */
@Getter
@Setter
public abstract class ItineraryDto {
    private UUID id = UUID.randomUUID();
    private String name;
    private Coordinates coordinates;
    private String description;
    private String author;
    private Date creationDate;
    private Date expiryDate;
    private ContentState state;
    // @JsonIgnore
    @JsonManagedReference
    private List<POIDto> itineraryPois = new ArrayList<POIDto>();
    @JsonIgnore
    @JsonBackReference
    private List<AuthenticatedUserDto> users = new ArrayList<AuthenticatedUserDto>();
    // @JsonIgnore
    @JsonManagedReference
    private List<AttachmentDto> attachments = new ArrayList<AttachmentDto>();
    @JsonIgnore
    @JsonBackReference
    private ContributionDto contribution;
    public ItineraryDto() {
    }
    public ItineraryDto(String name, Coordinates coordinates, String description, String author, Date creationDate, Date expiryDate, ContentState state, List<POIDto> itineraryPois, List<AuthenticatedUserDto> users, List<AttachmentDto> attachments, ContributionDto contribution) {
        this.name = name;
        this.coordinates = coordinates;
        this.description = description;
        this.author = author;
        this.creationDate = creationDate;
        this.expiryDate = expiryDate;
        this.state = state;
        this.itineraryPois = itineraryPois;
        this.users = users;
        this.attachments = attachments;
        this.contribution = contribution;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        ItineraryDto other = (ItineraryDto) obj;
        return other.getId().equals(this.getId())
                || other.getName().equalsIgnoreCase(this.getName());
    }
    /**
     * Method to get the invoking object, if it has the same state of the
     * one in parameter, and all its sub-entities, filtering them all by the state
     * in parameter
     * @param state the ContentState value that acts as a filter
     * @return the invoking object if it has same state of the one in
     * parameter, null otherwise
     */
    public ItineraryDto allWithState(ContentState state) {
        if (this.state == state) {
            this.setItineraryPois(this.itineraryPois.stream().filter(poiDto -> poiDto.getState().equals(ContentState.UPLOADED)).toList());
            this.setAttachments(this.attachments.stream().filter(attachmentDto -> attachmentDto.getState().equals(ContentState.UPLOADED)).toList());
            return this;
        } else {
            return null;
        }
    }
}
