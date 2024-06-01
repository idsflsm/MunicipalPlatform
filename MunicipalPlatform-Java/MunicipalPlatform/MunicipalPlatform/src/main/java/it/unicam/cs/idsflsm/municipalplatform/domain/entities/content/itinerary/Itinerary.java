package it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Content;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contribution;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Coordinates;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "itinerary_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "itinerary")
public abstract class Itinerary extends Content implements IItinerary {
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST/*, /* CascadeType.MERGE */})
    @JoinTable(name = "itinerary_pois",
            joinColumns = @JoinColumn(name = "itinerary_id", unique = false),
            inverseJoinColumns = @JoinColumn(name = "poi_id", unique = false))
    private List<POI> itineraryPois = new ArrayList<POI>();
//    @ManyToMany(mappedBy = "itineraries", fetch = FetchType.EAGER, cascade = {/* CascadeType.MERGE, */ CascadeType.PERSIST})
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    @JoinTable(
            name = "user_itinerary",
            joinColumns = @JoinColumn(name = "itinerary_id", unique = false),
            inverseJoinColumns = @JoinColumn(name = "user_id", unique = false)
    )
    private List<AuthenticatedUser> users = new ArrayList<AuthenticatedUser>();
    @OneToMany(mappedBy = "itinerary", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}, /* orphanRemoval = true, */
            fetch = FetchType.EAGER)
    private List<Attachment> attachments = new ArrayList<Attachment>();
    @OneToOne(mappedBy = "itinerary", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE /*, orphanRemoval = true */)
    private Contribution contribution;
    public Itinerary() {
    }
    public Itinerary
            (String name, Coordinates coordinates, String description,
             String author, Date creationDate, Date expiryDate, ContentState state, List<POI> itineraryPois, List<AuthenticatedUser> users, List<Attachment> attachments, Contribution contribution) {
        super(name, coordinates, description, author, creationDate, expiryDate, state);
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
        Itinerary other = (Itinerary) obj;
        return other.getId().equals(this.getId())
                || other.getName().equalsIgnoreCase(this.getName());
    }
    @Override
    public void detachFromEntities() {
        this.itineraryPois.forEach(poi -> poi.getPoiItineraries().remove(this));
        setItineraryPois(new ArrayList<POI>());
        this.users.forEach(authenticatedUser -> authenticatedUser.getItineraries().remove(this));
        setUsers(new ArrayList<AuthenticatedUser>());
//        this.attachments.forEach(attachment -> attachment.setItinerary(null));
//        setAttachments(new ArrayList<Attachment>());
//        this.contribution.setItinerary(null);
//        this.contribution = null;
    }
}
