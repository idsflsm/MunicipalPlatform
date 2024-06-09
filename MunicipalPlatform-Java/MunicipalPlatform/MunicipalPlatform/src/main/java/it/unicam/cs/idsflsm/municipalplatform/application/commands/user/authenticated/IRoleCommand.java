package it.unicam.cs.idsflsm.municipalplatform.application.commands.user.authenticated;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserRole;
public interface IRoleCommand {
    AuthenticatedUser getAuthenticatedUser();
    UserRole getRole();
    void execute();
}
