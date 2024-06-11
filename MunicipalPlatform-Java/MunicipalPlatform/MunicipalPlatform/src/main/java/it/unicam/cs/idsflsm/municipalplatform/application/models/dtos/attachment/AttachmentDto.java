package it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.ItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.POIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.report.ReportDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/**
 * Represents a DTO related to the entity Attachment.
 * It contains all fields with simple types
 * and the DTOs of entity fields
 */
@Getter
@Setter
public abstract class AttachmentDto {
    private UUID id = UUID.randomUUID();
    private String name;
    private String description;
    private String author;
    private Date creationDate;
    private Date expiryDate;
    private ContentState state;
    @JsonIgnore
    @JsonBackReference
    private POIDto poi;
    @JsonIgnore
    @JsonBackReference
    private ItineraryDto itinerary;
    @JsonManagedReference
    private List<ReportDto> reports = new ArrayList<ReportDto>();
    public AttachmentDto() {
    }
    public AttachmentDto(String name, String description, String author, Date creationDate, Date expiryDate, ContentState state, POIDto poi, ItineraryDto itinerary, List<ReportDto> reports) {
        this.name = name;
        this.description = description;
        this.author = author;
        this.creationDate = creationDate;
        this.expiryDate = expiryDate;
        this.state = state;
        this.poi = poi;
        this.itinerary = itinerary;
        this.reports = reports;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        AttachmentDto other = (AttachmentDto) obj;
        return other.getId().equals(this.getId())
                || other.getName().equalsIgnoreCase(this.getName());
    }
    /**
     * Method to get the invoking object, if it has the same state of the
     * one in parameter, and all its sub-entities, filtering them all by the state
     * in parameter
     *
     * @param state the ContentState value that acts as a filter
     * @return the invoking object if it has same state of the one in
     * parameter, null otherwise
     */
    public AttachmentDto allWithState(ContentState state) {
        if (this.state == state) {
            if (state.equals(ContentState.UPLOADED)) {
                this.setReports(new ArrayList<ReportDto>());
            }
            return this;
        } else {
            return null;
        }
    }
}
