package it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.ItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.POIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.contest.ContestDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contest;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
public class AuthenticatedTouristDto extends AuthenticatedUserDto {
    public AuthenticatedTouristDto() {
    }
    public AuthenticatedTouristDto(String username, String password, String name, String surname, List<POIDto> pois, List<ItineraryDto> itineraries, List<ContestDto> participatedContests, UserRole role) {
        super(username, password, name, surname, pois, itineraries, participatedContests, role);
    }
}
