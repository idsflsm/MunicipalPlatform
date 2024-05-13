package it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.report.Report;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
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
public abstract class AttachmentDto {
    private UUID id = UUID.randomUUID();
    private String name;
    private String description;
    private String author;
    private Date creationDate;
    private Date expiryDate;
    private ContentState state = ContentState.VALIDABLE;
    @JsonBackReference
    private POI poi;
    @JsonBackReference
    private Itinerary itinerary;
    @JsonManagedReference
    private List<Report> reports;
}
