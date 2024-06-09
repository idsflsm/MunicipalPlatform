package it.unicam.cs.idsflsm.municipalplatform.api.controllers.user;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidUserRoleException;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.user.IUserService;
import it.unicam.cs.idsflsm.municipalplatform.application.criterias.user.RoleRequestCriteria;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.RoleRequestDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.anonymous.AnonymousUserDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.anonymous.TouristDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedUserDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.user.authenticated.LoginRequest;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.user.authenticated.RegisterRequest;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.user.authenticated.SendRequestedRoleRequest;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.user.authenticated.UpdateUserRoleRequest;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.RoleRequest;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserPermission;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor(onConstructor_ = @Autowired)
public class UserController {
    /**
     * The service for User entities
     */
    private final IUserService _userService;

    /**
     * Method that registers a new user in the platform
     * @param request the request for registration
     * @return the AuthenticatedUser DTO if has been registered
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        AuthenticatedUserDto userDto = _userService.register(request);
        if (userDto != null) {
            return new ResponseEntity<>(userDto, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Method that logs in a user in the platform
     * @param request the request for logging in
     * @return the AuthenticatedUser DTO if has been logged in
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        AuthenticatedUserDto userDto = _userService.login(request);
        if (userDto != null) {
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Method that sends a role request for a user in the platform
     * @param idUser the UUID of the user performing the operation
     * @param request the request for sending a request for role changing
     * @return the RoleRequest DTO if has been sent
     */
    @PostMapping("/{idUser}/role/request")
    public ResponseEntity<?> sendRequestedRole(@PathVariable("idUser") UUID idUser, @RequestBody SendRequestedRoleRequest request) {
        try {
            if (_userService.appropriateUser(idUser, UserPermission.AUTHTOURIST_USER_ROLE_SEND)) {
                UserRole role = UserRole.fromString(request.getRole());
                RoleRequestDto roleRequestDto = new RoleRequestDto(idUser, request.getEmail(), role);
                RoleRequestDto result = _userService.sendRequestedRole(roleRequestDto);
                if (result != null) {
                    return new ResponseEntity<>(result, HttpStatus.OK);
                }
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (InvalidUserRoleException e1) {
            return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Method that retrieves a list of filtered role requests in the platform
     * @param idUser the UUID of the user performing the operation
     * @param username the username of desired users
     * @return the list of found RoleRequest DTO, based on params
     */
    @GetMapping("/role/request")
    public ResponseEntity<?> receiveRequestedRole(@RequestParam UUID idUser, @RequestParam(required = false) String username) {
        if (_userService.appropriateUser(idUser, UserPermission.ADMINISTRATOR_USER_ROLE_RECEIVE)) {
            Predicate<RoleRequest> predicate = RoleRequestCriteria.hasUsername(username);
            List<RoleRequestDto> result = _userService.receiveRequestedRoles(predicate);
            if (!result.isEmpty()) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    /**
     * Method that updates the role of a user in the platform
     * @param idUser the UUID of desired user
     * @param idAdmin the UUID of administrator
     * @param request the request for updating role of desired user
     * @return the AuthenticatedUser DTO if has been updated its role
     */
    @PutMapping("/{idUser}/role/request")
    public ResponseEntity<?> updateUserRole(@PathVariable("idUser") UUID idUser, @RequestParam(required = true) UUID idAdmin, @RequestBody UpdateUserRoleRequest request) {
        if (_userService.appropriateUser(idAdmin, UserPermission.ADMINISTRATOR_USER_ROLE_UPDATE)) {
            AuthenticatedUserDto result = _userService.updateUserRole(idUser, request.isAccept());
            if (result != null) {
                return new ResponseEntity<>("Successful role update.", HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    /**
     * Method that removes (sets to AUTHENTICATED_TOURIST) the role of a user in the platform
     * @param idUser the UUID of desired user
     * @param idAdmin the UUID of administrator
     * @return the AuthenticatedUser DTO if has been removed its role
     */
    @DeleteMapping("/{idUser}/role")
    public ResponseEntity<?> removeUserRole(@PathVariable("idUser") UUID idUser, @RequestParam UUID idAdmin) {
        if (_userService.appropriateUser(idAdmin, UserPermission.ADMINISTRATOR_USER_ROLE_DELETE)) {
            AuthenticatedUserDto result = _userService.removeUserRole(idUser);
            if (result != null) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    /**
     * Method that opens an anonymous session for a user in the platform,
     * giving him a session id that corresponds to an AnonymousUser entity
     * @return the AnonymousUser DTO if session has been opened
     */
    @PostMapping("/anonymous")
    public ResponseEntity<?> openAnonymousSession() {
        AnonymousUserDto result = _userService.openAnonymousSession();
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Method that closes an anonymous session for an AnonymousUser entity in the platform
     * @param id the UUID of desired anonymous session
     * @return the AnonymousUser DTO if session has been closed
     */
    @DeleteMapping("/anonymous/{id}")
    public ResponseEntity<?> closeAnonymousSession(@PathVariable("id") UUID id) {
        AnonymousUserDto userDto = new TouristDto(id);
        boolean result = _userService.closeAnonymousSession(userDto);
        if (result) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
