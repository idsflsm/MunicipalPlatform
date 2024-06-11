package it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.user;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.RoleRequestDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.anonymous.AnonymousUserDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedUserDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.user.authenticated.LoginRequest;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.user.authenticated.RegisterRequest;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.RoleRequest;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserPermission;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
/**
 * Interface for User service class. It provides methods to manipulate persistent
 * authenticated and anonymous users, and their requested roles, in the database
 */
public interface IUserService {
    void saveInRepository(AuthenticatedUser authenticatedUser);
    /**
     * Method that registers a new user in the platform
     *
     * @param request the registration request containing user's details
     * @return the AuthenticatedUser DTO if the new user is registered,
     * null otherwise
     */
    AuthenticatedUserDto register(RegisterRequest request);
    /**
     * Method that logs in a user in the platform
     *
     * @param request the login request containing user's email and password
     * @return An AuthenticatedUser DTO if the user is logged in,
     * null otherwise
     */
    AuthenticatedUserDto login(LoginRequest request);
    /**
     * Method that sends a role request for a user in the platform
     *
     * @param requestDto the RoleRequest DTO containing user's username and the requested role
     * @return the RoleRequest DTO if the role request has been sent,
     * null otherwise
     */
    RoleRequestDto sendRequestedRole(RoleRequestDto requestDto);
    /**
     * Method that retrieves a list of filtered role requests in the platform
     *
     * @param predicate the predicate for filtering
     * @return the list of RoleRequest DTOs that match the given predicate,
     * or an empty list if there are no RoleRequest entities that match
     */
    List<RoleRequestDto> receiveRequestedRoles(Predicate<RoleRequest> predicate);
    /**
     * Method that updates the role of a user in the platform
     *
     * @param idUser the UUID of the AuthenticatedUser entity whose role is to be updated
     * @param accept boolean flag indicating whether the role update request is accepted or not
     * @return the AuthenticatedUser DTO if the user with the given UUID exists
     * and there is a role request for the user,
     * null otherwise
     */
    AuthenticatedUserDto updateUserRole(UUID idUser, boolean accept);
    /**
     * Method that removes (sets to AUTHENTICATED_TOURIST) the role of a user in the platform
     *
     * @param idUser he UUID of the AuthenticatedUser entity whose role is to be removed
     * @return the AuthenticatedUser DTO if the user with the given UUID exists
     * and the user is not an administrator,
     * null otherwise
     */
    AuthenticatedUserDto removeUserRole(UUID idUser);
    /**
     * Method that finds a user in the platform by its username
     *
     * @param username the username of the AuthenticatedUser entity to be found
     * @return the AuthenticatedUser DTO if the user with the given username exists,
     * null otherwise
     */
    AuthenticatedUserDto findByUsername(String username);
    /**
     * Method that finds a user in the platform by its unique identifier
     *
     * @param id the UUID of the AuthenticatedUser entity to be found
     * @return the AuthenticatedUser DTO if the user with the given id exists,
     * null otherwise
     */
    AuthenticatedUserDto findById(UUID id);
    /**
     * Method that checks if a user has a specific permission
     *
     * @param idUser     The UUID of the AuthenticatedUser entity to be checked
     * @param permission the appropriate UserPermission value
     * @return true if the user with the given unique identifier has the specified permission,
     * false otherwise
     */
    boolean appropriateUser(UUID idUser, UserPermission permission);
    /**
     * Method that opens an anonymous session for a user in the platform,
     * giving him a session id that corresponds to an AnonymousUser entity
     *
     * @return the AnonymousUser DTO if a session with the generated UUID does not exist,
     * null otherwise
     */
    AnonymousUserDto openAnonymousSession();
    /**
     * Method that closes an anonymous session for an AnonymousUser entity in the platform
     *
     * @param userDto the AnonymousUser DTO representing the anonymous user session to be closed
     * @return true if the session exists and is successfully closed,
     * false otherwise
     */
    boolean closeAnonymousSession(AnonymousUserDto userDto);
}
