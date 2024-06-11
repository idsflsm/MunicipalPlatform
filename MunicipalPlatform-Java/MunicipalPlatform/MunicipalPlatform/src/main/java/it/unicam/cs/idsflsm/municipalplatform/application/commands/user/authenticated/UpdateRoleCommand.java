package it.unicam.cs.idsflsm.municipalplatform.application.commands.user.authenticated;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * Command class for updating the role
 * to a particular user
 */
@Getter
@AllArgsConstructor
public class UpdateRoleCommand implements IRoleCommand {
    /**
     * The user to whom to apply the role change
     */
    private final AuthenticatedUser authenticatedUser;
    /**
     * The role to assign to the specified user
     */
    private final UserRole role;
    @Override
    public void execute() {
        this.authenticatedUser.setRole(role);
    }
}
