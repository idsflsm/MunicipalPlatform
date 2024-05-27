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
@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "poi_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "poi")
public abstract class POI extends Content implements IPOI {
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "itineraryPois", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Itinerary> poiItineraries = new ArrayList<Itinerary>();
    @ManyToMany(mappedBy = "pois", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<AuthenticatedUser> users = new ArrayList<AuthenticatedUser>();
    @OneToMany(mappedBy = "poi", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true,
            fetch = FetchType.EAGER)
    private List<Attachment> attachments = new ArrayList<Attachment>();
    @OneToOne(mappedBy = "poi", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Contribution contribution;
    public POI() {
    }
    public POI
            (UUID id, String name, Coordinates coordinates, String description,
             String author, Date creationDate, Date expiryDate, ContentState state, List<Itinerary> poiItineraries, List<AuthenticatedUser> users, List<Attachment> attachments, Contribution contribution) {
        super(id, name, coordinates, description, author, creationDate, expiryDate, state);
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
        return other.getId().equals(this.getId());
    }
}
