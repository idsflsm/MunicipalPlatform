package it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Content;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contribution;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedTourist;
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
    @ManyToMany
    @JoinTable(name = "poi_itineraries",
            joinColumns = @JoinColumn(name = "poi_id"),
            inverseJoinColumns = @JoinColumn(name = "itinerary_id"))
    private List<Itinerary> poiItineraries = new ArrayList<>();
    @ManyToMany(mappedBy = "pois")
    private List<AuthenticatedTourist> tourists = new ArrayList<>();
    @OneToMany(mappedBy = "poi", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attachment> attachments = new ArrayList<>();
    @OneToMany(mappedBy = "poi")
    private List<Contribution> contributions = new ArrayList<>();
    public POI() {
    }
    public POI
            (UUID id, String name, Coordinates coordinates, String description,
             String author, Date creationDate, Date expiryDate, ContentState state, List<Itinerary> poiItineraries, List<AuthenticatedTourist> tourists, List<Attachment> attachments) {
        super(id, name, coordinates, description, author, creationDate, expiryDate, state);
        this.poiItineraries = poiItineraries;
        this.tourists = tourists;
        this.attachments = attachments;
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
