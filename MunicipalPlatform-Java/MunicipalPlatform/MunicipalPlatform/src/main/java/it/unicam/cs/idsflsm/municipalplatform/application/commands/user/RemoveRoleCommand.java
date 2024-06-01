package it.unicam.cs.idsflsm.municipalplatform.application.commands.user;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
@Getter
@RequiredArgsConstructor
public class RemoveRoleCommand implements IRoleCommand {
    private final AuthenticatedUser authenticatedUser;
    private final UserRole role = UserRole.AUTHENTICATED_TOURIST;
    @Override
    public void execute() {
        this.authenticatedUser.setRole(UserRole.AUTHENTICATED_TOURIST);
    }
}
