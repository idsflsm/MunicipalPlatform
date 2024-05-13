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
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id = UUID.randomUUID();
    @ManyToOne(fetch = FetchType.LAZY)
    private Contest contest;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poi_id")
    private POI poi;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itinerary_id")
    private Itinerary itinerary;
    @Column(name = "content_state", nullable = false, unique = false)
    private ContentState state = ContentState.VALIDABLE;
    @Column(name = "contest_result", nullable = false, unique = false)
    private ContestResult result = ContestResult.LOSER;
    public Contribution() {
    }
    public Contribution(UUID id, Contest contest, POI poi, Itinerary itinerary, ContentState state, ContestResult result) {
        this.id = (id != null) ? id : UUID.randomUUID();
        this.contest = contest;
        this.poi = poi;
        this.itinerary = itinerary;
        this.state = state;
        this.result = result;
    }
}
