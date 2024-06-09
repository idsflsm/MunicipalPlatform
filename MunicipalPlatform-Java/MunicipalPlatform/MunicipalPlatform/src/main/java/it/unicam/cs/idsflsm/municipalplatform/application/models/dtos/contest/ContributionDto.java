package it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.contest;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.ItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.POIDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contest;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contribution;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContestResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
/**
 * Represents a DTO related to the entity Contribution.
 * It contains all fields with simple types
 * and the DTOs of entity fields
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContributionDto {
    private UUID id = UUID.randomUUID();
    @JsonIgnore
    @JsonBackReference
    private ContestDto contest;
    // @JsonIgnore
    @JsonManagedReference
    private POIDto poi;
    // @JsonIgnore
    @JsonManagedReference
    private ItineraryDto itinerary;
    private ContentState state = ContentState.VALIDABLE;
    private ContestResult result = ContestResult.LOSER;
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        ContributionDto other = (ContributionDto) obj;
        return other.getId().equals(this.getId());
    }
}
