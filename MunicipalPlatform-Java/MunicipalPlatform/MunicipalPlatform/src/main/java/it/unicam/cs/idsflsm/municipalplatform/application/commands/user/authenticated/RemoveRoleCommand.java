package it.unicam.cs.idsflsm.municipalplatform.application.commands.user.authenticated;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserRole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
/**
 * Command class for removing (setting to AUTHENTICATED_TOURIST) the role
 * to a particular user
 */
@Getter
@RequiredArgsConstructor
public class RemoveRoleCommand implements IRoleCommand {
    /**
     * The user to whom to apply the role change
     */
    private final AuthenticatedUser authenticatedUser;
    /**
     * The role to assign to the specified user
     */
    private final UserRole role = UserRole.AUTHENTICATED_TOURIST;
    @Override
    public void execute() {
        this.authenticatedUser.setRole(UserRole.AUTHENTICATED_TOURIST);
    }
}
