package it.unicam.cs.idsflsm.municipalplatform.application.builders.user.authenticated;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contest;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
/**
 * General builder class for AuthenticatedUser builders
 */
@Component
@Getter
@Setter
@NoArgsConstructor
public abstract class AuthenticatedUserBuilder implements IAuthenticatedUserBuilder {
    /**
     * The username of the authenticated user
     */
    private String username;
    /**
     * The password of the authenticated user
     */
    private String password;
    /**
     * The name of the authenticated user
     */
    private String name;
    /**
     * The surname of the authenticated user
     */
    private String surname;
    /**
     * The list of saved POIs
     */
    private List<POI> pois = new ArrayList<>();
    /**
     * The list of saved itineraries
     */
    private List<Itinerary> itineraries = new ArrayList<>();
    /**
     * The list of contests to which the user participates
     */
    private List<Contest> participatedContests = new ArrayList<>();
    /**
     * The command to execute the modification or elimination
     * of the user's own role
     */
    private UserRole role;
    @Override
    public abstract AuthenticatedUser build();
}
