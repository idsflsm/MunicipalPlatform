package it.unicam.cs.idsflsm.municipalplatform.application.builders.user.authenticated;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthorizedContributor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
/**
 * Specific builder class for the building of AuthorizedContributor instances
 */
@Component
@NoArgsConstructor
public class AuthorizedContributorBuilder extends AuthenticatedUserBuilder {
    @Override
    public AuthenticatedUser build() {
        return new AuthorizedContributor(
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
