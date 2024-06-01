package it.unicam.cs.idsflsm.municipalplatform.application.builders.user;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.Animator;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
@Component
@NoArgsConstructor
public class AnimatorBuilder extends AuthenticatedUserBuilder {
    @Override
    public AuthenticatedUser build() {
        return new Animator(
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
