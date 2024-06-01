package it.unicam.cs.idsflsm.municipalplatform.application.builders.user;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.ItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.POIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.contest.ContestDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contest;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.*;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Supplier;
@Component
@Getter
@Setter
@NoArgsConstructor
public abstract class AuthenticatedUserBuilder implements IAuthenticatedUserBuilder {
    private String username;
    private String password;
    private String name;
    private String surname;
    private List<POI> pois = new ArrayList<POI>();
    private List<Itinerary> itineraries = new ArrayList<Itinerary>();
    private List<Contest> participatedContests = new ArrayList<Contest>();
    private UserRole role;
    @Override
    public abstract AuthenticatedUser build();
}