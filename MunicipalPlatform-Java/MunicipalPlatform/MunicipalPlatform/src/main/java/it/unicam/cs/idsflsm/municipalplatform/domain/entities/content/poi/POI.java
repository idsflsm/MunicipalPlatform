package it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Content;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contribution;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Coordinates;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * Represents a Point Of Interest on the platform. It contains information such as
 * the list of its attachments and the associated contest contribution if exists
 */
@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "poi_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "poi")
public abstract class POI extends Content implements IPOI {
    /**
     * The list of itineraries that contain the poi
     */
//    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "itineraryPois", cascade = {CascadeType.PERSIST, /* CascadeType.MERGE */})
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST/*, /* CascadeType.MERGE */})
    @JoinTable(
        name = "itinerary_pois",
        joinColumns = @JoinColumn(name = "poi_id", unique = false),
        inverseJoinColumns = @JoinColumn(name = "itinerary_id", unique = false))
    private List<Itinerary> poiItineraries = new ArrayList<Itinerary>();
    /**
     * The list of authenticated users that saved the poi
     */
//    @ManyToMany(mappedBy = "pois", fetch = FetchType.EAGER, cascade = {/* CascadeType.MERGE, */ CascadeType.PERSIST})
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    @JoinTable(
        name = "user_poi",
        joinColumns = @JoinColumn(name = "poi_id", unique = false),
        inverseJoinColumns = @JoinColumn(name = "user_id", unique = false)
    )
    private List<AuthenticatedUser> users = new ArrayList<AuthenticatedUser>();
    /**
     * The list of attachments associated to the poi
     */
    @OneToMany(mappedBy = "poi", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true,
            fetch = FetchType.EAGER)
    private List<Attachment> attachments = new ArrayList<Attachment>();
    /**
     * The corresponding contest contribution (if exists)
     */
    @OneToOne(mappedBy = "poi", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE /*, orphanRemoval = true */)
    private Contribution contribution;
    public POI() {
    }
    public POI
            (String name, Coordinates coordinates, String description,
             String author, Date creationDate, Date expiryDate, ContentState state, List<Itinerary> poiItineraries, List<AuthenticatedUser> users, List<Attachment> attachments, Contribution contribution) {
        super(name, coordinates, description, author, creationDate, expiryDate, state);
        this.poiItineraries = poiItineraries;
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
        POI other = (POI) obj;
        return other.getId().equals(this.getId())
                || other.getName().equalsIgnoreCase(this.getName());
    }
    @Override
    public void detachFromEntities() {
        this.poiItineraries.forEach(itinerary -> itinerary.getItineraryPois().remove(this));
        setPoiItineraries(new ArrayList<Itinerary>());
        this.users.forEach(authenticatedUser -> authenticatedUser.getPois().remove(this));
        setUsers(new ArrayList<AuthenticatedUser>());
//        this.attachments.forEach(attachment -> attachment.setItinerary(null));
//        setAttachments(new ArrayList<Attachment>());
//        this.contribution.setPoi(null);
//        this.contribution = null;
    }
//    @PreRemove
//    @Override
//    public void checkItinerariesSize() {
//        this.poiItineraries.forEach(itinerary -> {
//            if (itinerary.getItineraryPois().size() < 3) {
//                itinerary.detachFromEntities();
//                EntityManager entityManager
//            }
//        });
//    }
}
