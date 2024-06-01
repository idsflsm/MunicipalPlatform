package it.unicam.cs.idsflsm.municipalplatform.application.builders.user;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.Administrator;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
@Component
@NoArgsConstructor
public class AdministratorBuilder extends AuthenticatedUserBuilder {
    @Override
    public AuthenticatedUser build() {
        return new Administrator(
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
