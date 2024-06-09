package it.unicam.cs.idsflsm.municipalplatform.application.commands.user.authenticated;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateRoleCommand implements IRoleCommand {
    private final AuthenticatedUser authenticatedUser;
    private final UserRole role;
    @Override
    public void execute() {
        this.authenticatedUser.setRole(role);
    }
}
