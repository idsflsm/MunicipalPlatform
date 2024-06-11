package it.unicam.cs.idsflsm.municipalplatform.application.commands.user.authenticated;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserRole;
/**
 * Interface that represents a command to modify a user's role
 */
public interface IRoleCommand {
    AuthenticatedUser getAuthenticatedUser();
    UserRole getRole();
    /**
     * Method that enables executing the command, that can
     * be associated to a particular user
     */
    void execute();
}
