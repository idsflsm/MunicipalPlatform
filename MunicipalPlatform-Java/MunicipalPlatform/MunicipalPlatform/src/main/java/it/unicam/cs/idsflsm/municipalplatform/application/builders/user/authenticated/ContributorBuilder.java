package it.unicam.cs.idsflsm.municipalplatform.application.builders.user.authenticated;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.Contributor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
/**
 * Specific builder class for the building of Contributor instances
 */
@Component
@NoArgsConstructor
public class ContributorBuilder extends AuthenticatedUserBuilder {
    @Override
    public AuthenticatedUser build() {
        return new Contributor(
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
