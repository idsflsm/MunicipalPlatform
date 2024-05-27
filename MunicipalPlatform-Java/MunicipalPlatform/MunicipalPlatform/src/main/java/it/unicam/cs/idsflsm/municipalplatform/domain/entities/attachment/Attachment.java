package it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.report.Report;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "attachment_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "attachment")
public abstract class Attachment implements IAttachment {
    @Id
    // @GeneratedValue(strategy = GenerationType.UUID)
@Column(updatable = false, nullable = false)
    private UUID id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "description", nullable = true, unique = false)
    private String description;
    @Column(name = "author", nullable = false, unique = false)
    private String author;
    @Embedded
    @AttributeOverride(name = "day", column = @Column(name = "attachment_creation_date_day"))
    @AttributeOverride(name = "month", column = @Column(name = "attachment_creation_date_month"))
    @AttributeOverride(name = "year", column = @Column(name = "attachment_creation_date_year"))
    private Date creationDate;
    @Embedded
    @AttributeOverride(name = "day", column = @Column(name = "attachment_expiry_date_day"))
    @AttributeOverride(name = "month", column = @Column(name = "attachment_expiry_date_month"))
    @AttributeOverride(name = "year", column = @Column(name = "attachment_expiry_date_year"))
    private Date expiryDate;
    @Column(name = "content_state", nullable = false, unique = false)
    private ContentState state;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "poi", nullable = true)
    private POI poi;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "itinerary", nullable = true)
    private Itinerary itinerary;
    @OneToMany(mappedBy = "attachment", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true,
            fetch = FetchType.EAGER)
    private List<Report> reports = new ArrayList<Report>();
    public Attachment() {
    }
    public Attachment
            (UUID id, String name, String description, String author,
             Date creationDate, Date expiryDate, ContentState state, POI poi, Itinerary itinerary, List<Report> reports) {
        
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
    public void setNotNullPoi(POI poi) {
        if (this.poi != null) {
            this.setPoi(poi);
        }
    }
    public void setNotNullItinerary(Itinerary itinerary) {
        if (this.itinerary != null) {
            this.setItinerary(itinerary);
        }
    }
}
