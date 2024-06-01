package it.unicam.cs.idsflsm.municipalplatform.application.builders.user;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.Curator;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
@Component
@NoArgsConstructor
public class CuratorBuilder extends AuthenticatedUserBuilder {
    @Override
    public AuthenticatedUser build() {
        return new Curator(
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
