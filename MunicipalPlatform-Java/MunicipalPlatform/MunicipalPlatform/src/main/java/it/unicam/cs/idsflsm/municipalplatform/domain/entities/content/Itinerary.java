package it.unicam.cs.idsflsm.municipalplatform.domain.entities.content;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contribution;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedTourist;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Coordinates;
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
@DiscriminatorColumn(name = "itinerary_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "itinerary")
public abstract class Itinerary extends Content implements IItinerary {
    @ManyToMany
    @JoinTable(name = "itinerary_pois",
            joinColumns = @JoinColumn(name = "itinerary_id"),
            inverseJoinColumns = @JoinColumn(name = "poi_id"))
    private List<POI> itineraryPois = new ArrayList<>();
    @ManyToMany(mappedBy = "itineraries")
    private List<AuthenticatedTourist> tourists = new ArrayList<>();
    @OneToMany(mappedBy = "itinerary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attachment> attachments = new ArrayList<>();
    @OneToMany(mappedBy = "itinerary")
    private List<Contribution> contributions = new ArrayList<>();
    public Itinerary() {
    }
    public Itinerary
            (UUID id, String name, Coordinates coordinates, String description,
             String author, Date creationDate, Date expiryDate, ContentState state, List<POI> itineraryPois, List<AuthenticatedTourist> tourists, List<Attachment> attachments) {
        super(id, name, coordinates, description, author, creationDate, expiryDate, state);
        this.itineraryPois = itineraryPois;
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
        Itinerary other = (Itinerary) obj;
        return other.getId().equals(this.getId());
    }
}
