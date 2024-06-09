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

/**
 * Represents generic content on the platform. It contains general information,
 * including name, coordinates, creation/expiry dates and content state
 */
@MappedSuperclass
@Getter
@Setter
public abstract class Content implements IContent {
    /**
     * The unique identifier of the content
     */
    @Id
    // @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;
    /**
     * The name of the content
     */
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    /**
     * The coordinates of the content
     */
    @Embedded
    @Column(name = "coordinates", nullable = false, unique = true)
    private Coordinates coordinates;
    /**
     * The description of the content
     */
    @Column(name = "description", nullable = true, unique = false)
    private String description;
    /**
     * The author of the content
     */
    @Column(name = "author", nullable = false, unique = false)
    private String author;
    /**
     * The creation date of the content
     */
    @Embedded
    @AttributeOverride(name = "day", column = @Column(name = "content_creation_date_day"))
    @AttributeOverride(name = "month", column = @Column(name = "content_creation_date_month"))
    @AttributeOverride(name = "year", column = @Column(name = "content_creation_date_year"))
    private Date creationDate;
    /**
     * The expiry date of the content
     */
    @Embedded
    @AttributeOverride(name = "day", column = @Column(name = "content_expiry_date_day"))
    @AttributeOverride(name = "month", column = @Column(name = "content_expiry_date_month"))
    @AttributeOverride(name = "year", column = @Column(name = "content_expiry_date_year"))
    private Date expiryDate;
    /**
     * The state of the content
     */
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
