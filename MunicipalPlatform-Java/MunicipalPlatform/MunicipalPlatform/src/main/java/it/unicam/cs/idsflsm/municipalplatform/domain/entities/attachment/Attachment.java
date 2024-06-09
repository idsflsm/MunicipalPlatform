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

/**
 * Represents an attachment, associated to a content on the platform. It contains general information,
 * including name, description, author and the list of active reports
 */
@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "attachment_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "attachment")
public abstract class Attachment implements IAttachment {
    /**
     * The unique identifier of the attachment
     */
    @Id
    // @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;
    /**
     * The name of the attachment
     */
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    /**
     * The description of the attachment
     */
    @Column(name = "description", nullable = true, unique = false)
    private String description;
    /**
     * The author of the attachment
     */
    @Column(name = "author", nullable = false, unique = false)
    private String author;
    /**
     * The creation date of the attachment
     */
    @Embedded
    @AttributeOverride(name = "day", column = @Column(name = "attachment_creation_date_day"))
    @AttributeOverride(name = "month", column = @Column(name = "attachment_creation_date_month"))
    @AttributeOverride(name = "year", column = @Column(name = "attachment_creation_date_year"))
    private Date creationDate;
    /**
     * The expiry date of the attachment
     */
    @Embedded
    @AttributeOverride(name = "day", column = @Column(name = "attachment_expiry_date_day"))
    @AttributeOverride(name = "month", column = @Column(name = "attachment_expiry_date_month"))
    @AttributeOverride(name = "year", column = @Column(name = "attachment_expiry_date_year"))
    private Date expiryDate;
    /**
     * The state of the attachment
     */
    @Enumerated(value = EnumType.STRING)
    @Column(name = "content_state", nullable = false, unique = false)
    private ContentState state;
    /**
     * The POI associated to the attachment (if exists)
     */
    @ManyToOne(fetch = FetchType.EAGER /*, cascade = CascadeType.REMOVE */)
    @JoinColumn(name = "poi", nullable = true)
    private POI poi;
    /**
     * The Itinerary associated to the attachment (if exists)
     */
    @ManyToOne(fetch = FetchType.EAGER /*, cascade = CascadeType.REMOVE */)
    @JoinColumn(name = "itinerary", nullable = true)
    private Itinerary itinerary;
    /**
     * The list of active reports of the attachment
     */
    @OneToMany(mappedBy = "attachment", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}, /* orphanRemoval = true, */
            fetch = FetchType.EAGER)
    private List<Report> reports = new ArrayList<Report>();
    public Attachment() {
    }
    public Attachment
            (String name, String description, String author,
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
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Attachment other = (Attachment) obj;
        return other.getId().equals(this.getId())
                || other.getName().equalsIgnoreCase(this.getName());
    }
    @Override
    public void setNotNullPoi(POI poi) {
        if (this.poi != null) {
            this.setPoi(poi);
        }
    }
    @Override
    public void setNotNullItinerary(Itinerary itinerary) {
        if (this.itinerary != null) {
            this.setItinerary(itinerary);
        }
    }
    @Override
    public void detachFromEntities() {
        if (this.poi != null) {
            this.poi.getAttachments().remove(this);
        }
        setNotNullPoi(null);
        if (this.itinerary != null) {
            this.itinerary.getAttachments().remove(this);
        }
        setNotNullItinerary(null);
    }
}
