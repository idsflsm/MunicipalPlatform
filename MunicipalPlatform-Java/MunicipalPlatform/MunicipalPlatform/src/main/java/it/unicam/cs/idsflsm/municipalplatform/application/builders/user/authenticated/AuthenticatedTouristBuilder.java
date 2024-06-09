package it.unicam.cs.idsflsm.municipalplatform.application.builders.user.authenticated;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedTourist;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
/**
 * Specific builder class for the building of AuthenticatedTourist instances
 */
@Component
@NoArgsConstructor
public class AuthenticatedTouristBuilder extends AuthenticatedUserBuilder {
    @Override
    public AuthenticatedUser build() {
        return new AuthenticatedTourist(
                this.getUsername(),
                this.getPassword(),
                this.getName(),
                this.getSurname(),
                this.getPois(),
                this.getItineraries(),
                this.getParticipatedContests(),
                this.getRole()
        );
    }
}
