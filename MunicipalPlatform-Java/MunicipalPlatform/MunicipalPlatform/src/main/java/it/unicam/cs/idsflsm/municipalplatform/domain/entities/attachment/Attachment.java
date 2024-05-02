package it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment;

import com.fasterxml.jackson.annotation.JsonBackReference;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Content;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.POI;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id = UUID.randomUUID();
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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "poi", nullable = true)
//    @JsonBackReference
    private POI poi;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "itinerary", nullable = true)
//    @JsonBackReference
    private Itinerary itinerary;
    @OneToMany(mappedBy = "attachment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Report> reports = new ArrayList<>();
    public Attachment() {
    }
    public Attachment
            (UUID id, String name, String description, String author,
             Date creationDate, Date expiryDate, ContentState state, POI poi, Itinerary itinerary, List<Report> reports) {
        this.id = (id != null) ? id : UUID.randomUUID();
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
}