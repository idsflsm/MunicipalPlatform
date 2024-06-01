package it.unicam.cs.idsflsm.municipalplatform.api.controllers.user;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidUserRoleException;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.user.IUserService;
import it.unicam.cs.idsflsm.municipalplatform.application.criterias.user.RoleRequestCriteria;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.RoleRequestDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.anonymous.AnonymousUserDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.anonymous.TouristDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedUserDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.user.LoginRequest;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.user.RegisterRequest;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.user.SendRequestedRoleRequest;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.user.UpdateUserRoleRequest;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserPermission;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor(onConstructor_ = @Autowired)
public class UserController {
    private final IUserService _userService;
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request
    ) {
        AuthenticatedUserDto userDto = _userService.register(request);
        if (userDto != null) {
            return new ResponseEntity<>(userDto, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        AuthenticatedUserDto userDto = _userService.login(request);
        if (userDto != null) {
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/role/requested/{idUser}")
    public ResponseEntity<?> sendRequestedRole(@PathVariable("idUser") UUID idUser, @RequestBody SendRequestedRoleRequest request) {
        if (_userService.appropriateUser(idUser, UserPermission.AUTHTOURIST_USER_ROLE_SEND)) {
            try {
                UserRole role = UserRole.fromString(request.getRole());
                RoleRequestDto roleRequestDto = new RoleRequestDto(request.getEmail(), role);
                RoleRequestDto result = _userService.sendRequestedRole(roleRequestDto);
                if (result != null) {
                    return new ResponseEntity<>(result, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            } catch (InvalidUserRoleException e1) {
                return new ResponseEntity<>(e1.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @GetMapping("/role/requested")
    public ResponseEntity<?> receiveRequestedRole(@RequestParam UUID idUser, @RequestParam String username, @RequestParam UserRole role) {
        if (_userService.appropriateUser(idUser, UserPermission.ADMINISTRATOR_USER_ROLE_RECEIVE)) {
            List<RoleRequestDto> result = _userService.receiveRequestedRoles(RoleRequestCriteria.hasUsername(username));
            if (!result.isEmpty()) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @PutMapping("/role/requested/{idUser}")
    public ResponseEntity<?> updateUserRole(@PathVariable("idUser") UUID idUser, @RequestParam UUID idAdmin, @RequestBody UpdateUserRoleRequest request) {
        if (_userService.appropriateUser(idAdmin, UserPermission.ADMINISTRATOR_USER_ROLE_UPDATE)) {
            AuthenticatedUserDto result = _userService.updateUserRole(idUser, request.isAccept());
            if (result != null) {
                return new ResponseEntity<>("Successful role update.",HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @DeleteMapping("/role/{idUser}")
    public ResponseEntity<?> removeUserRole(@PathVariable("idUser") UUID idUser, @RequestParam UUID idAdmin) {
        if (_userService.appropriateUser(idAdmin, UserPermission.ADMINISTRATOR_USER_ROLE_DELETE)) {
            AuthenticatedUserDto result = _userService.removeUserRole(idUser);
            if (result != null) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @PostMapping("/anonymous")
    public ResponseEntity<?> openAnonymousSession() {
        AnonymousUserDto result = _userService.openAnonymousSession();
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/anonymous/{id}")
    public ResponseEntity<?> closeAnonymousSession(@PathVariable("id") UUID id) {
        AnonymousUserDto userDto = new TouristDto(id);
        boolean result = _userService.closeAnonymousSession(userDto);
        if (result) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
