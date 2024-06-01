package it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.ItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.POIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.contest.ContestDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserRole;

import java.util.List;
import java.util.UUID;
public class AnimatorDto extends AuthenticatedUserDto {
    public AnimatorDto() {
    }
    public AnimatorDto(String username, String password, String name, String surname, List<POIDto> pois, List<ItineraryDto> itineraries, List<ContestDto> participatedContests, UserRole role) {
        super(username, password, name, surname, pois, itineraries, participatedContests, role);
    }
}
