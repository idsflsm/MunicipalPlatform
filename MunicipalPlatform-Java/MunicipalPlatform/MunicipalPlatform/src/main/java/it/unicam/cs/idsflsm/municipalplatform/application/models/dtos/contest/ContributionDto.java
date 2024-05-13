package it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.contest;
import com.fasterxml.jackson.annotation.JsonBackReference;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contest;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContestResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContributionDto {
    private UUID id = UUID.randomUUID();
    @JsonBackReference
    private Contest contest;
    @JsonBackReference
    private POI poi;
    @JsonBackReference
    private Itinerary itinerary;
    private ContentState state = ContentState.VALIDABLE;
    private ContestResult result = ContestResult.LOSER;
}
