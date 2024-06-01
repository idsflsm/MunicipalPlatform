package it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.ItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.POIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.contest.ContestDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
public abstract class AuthenticatedUserDto {
    private UUID id = UUID.randomUUID();
    private String username;
    @JsonIgnore
    private String password;
    private String name;
    private String surname;
    @JsonManagedReference
    private List<POIDto> pois = new ArrayList<POIDto>();
    @JsonManagedReference
    private List<ItineraryDto> itineraries = new ArrayList<ItineraryDto>();
    @JsonIgnore
    @JsonBackReference
    private List<ContestDto> participatedContests = new ArrayList<ContestDto>();
    private UserRole role = UserRole.AUTHENTICATED_TOURIST;
    public AuthenticatedUserDto() {
    }
    public AuthenticatedUserDto(String username, String password, String name, String surname, List<POIDto> pois, List<ItineraryDto> itineraries, List<ContestDto> participatedContests, UserRole role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.pois = pois;
        this.itineraries = itineraries;
        this.participatedContests = participatedContests;
        this.role = role;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        AuthenticatedUserDto other = (AuthenticatedUserDto) obj;
        return other.getId().equals(this.getId());
    }
}
