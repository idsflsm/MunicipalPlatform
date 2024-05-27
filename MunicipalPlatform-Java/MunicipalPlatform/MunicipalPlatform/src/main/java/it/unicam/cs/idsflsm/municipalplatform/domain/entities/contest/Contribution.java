package it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Content;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContestResult;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Entity
@Getter
@Setter
@Table(name = "contribution")
public class Contribution {
    @Id
    // @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;
    @ManyToOne(fetch = FetchType.EAGER)
    private Contest contest;
    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "poi_id")
    private POI poi;
    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "itinerary_id")
    private Itinerary itinerary;
    @Enumerated(EnumType.STRING)
    @Column(name = "content_state", nullable = false, unique = false)
    private ContentState state = ContentState.VALIDABLE;
    @Enumerated(EnumType.STRING)
    @Column(name = "contest_result", nullable = false, unique = false)
    private ContestResult result = ContestResult.LOSER;
    public Contribution() {
    }
    public Contribution(UUID id, Contest contest, POI poi, Itinerary itinerary, ContentState state, ContestResult result) {
        
        this.contest = contest;
        this.poi = poi;
        this.itinerary = itinerary;
        this.state = state;
        this.result = result;
    }
    public Content getContent() {
        if (this.poi != null && this.itinerary == null) {
            return poi;
        } else {
            return itinerary;
        }
    }
    public void setContent(Content content) {
        var activeContent = getContent();
        if (content != null) {
            if (activeContent.getClass() == content.getClass()) {
                if (activeContent instanceof POI) {
                    this.poi = (POI) content;
                } else if (activeContent instanceof Itinerary) {
                    this.itinerary = (Itinerary) content;
                }
            }
        } else {
            this.poi = null;
            this.itinerary = null;
        }
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
