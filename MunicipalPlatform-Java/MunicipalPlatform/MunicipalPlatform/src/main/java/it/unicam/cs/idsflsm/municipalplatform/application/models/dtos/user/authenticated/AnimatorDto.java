package it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.ItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.POIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.contest.ContestDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserRole;

import java.util.List;
/**
 * Represents a DTO related to the entity Animator.
 * It contains all fields with simple types
 * and the DTOs of entity fields
 */
public class AnimatorDto extends AuthenticatedUserDto {
    public AnimatorDto() {
    }
    public AnimatorDto(String username, String password, String name, String surname, List<POIDto> pois, List<ItineraryDto> itineraries, List<ContestDto> participatedContests, UserRole role) {
        super(username, password, name, surname, pois, itineraries, participatedContests, role);
    }
}
