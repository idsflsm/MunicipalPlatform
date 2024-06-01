package it.unicam.cs.idsflsm.municipalplatform.domain.entities.content;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Coordinates;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public abstract class Content implements IContent {
    @Id
    // @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Embedded
    @Column(name = "coordinates", nullable = false, unique = true)
    private Coordinates coordinates;
    @Column(name = "description", nullable = true, unique = false)
    private String description;
    @Column(name = "author", nullable = false, unique = false)
    private String author;
    @Embedded
    @AttributeOverride(name = "day", column = @Column(name = "content_creation_date_day"))
    @AttributeOverride(name = "month", column = @Column(name = "content_creation_date_month"))
    @AttributeOverride(name = "year", column = @Column(name = "content_creation_date_year"))
    private Date creationDate;
    @Embedded
    @AttributeOverride(name = "day", column = @Column(name = "content_expiry_date_day"))
    @AttributeOverride(name = "month", column = @Column(name = "content_expiry_date_month"))
    @AttributeOverride(name = "year", column = @Column(name = "content_expiry_date_year"))
    private Date expiryDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "content_state", nullable = false, unique = false)
    private ContentState state;
    public Content() {
    }
    public Content
            (String name, Coordinates coordinates, String description,
             String author, Date creationDate, Date expiryDate, ContentState state) {
        this.name = name;
        this.coordinates = coordinates;
        this.description = description;
        this.author = author;
        this.creationDate = creationDate;
        this.expiryDate = expiryDate;
        this.state = state;
    }
}
