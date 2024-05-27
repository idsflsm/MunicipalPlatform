package it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.user;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.RoleRequestDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.anonymous.AnonymousUserDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedUserDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.user.LoginRequest;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.user.RegisterRequest;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.RoleRequest;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserPermission;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public interface IUserService {
    AuthenticatedUserDto register(RegisterRequest request);
    AuthenticatedUserDto login(LoginRequest request);
    RoleRequestDto sendRequestedRole(RoleRequestDto requestDto);
    List<RoleRequestDto> receiveRequestedRoles(Predicate<RoleRequest> predicate);
    void saveInRepository(AuthenticatedUser authenticatedUser);
    AuthenticatedUserDto updateUserRole(UUID idUser, boolean accept);
    AuthenticatedUserDto removeUserRole(UUID idUser);
    RoleRequestDto findRoleRequestByUsername(String username);
    AuthenticatedUserDto findByUsername(String username);
    AuthenticatedUserDto findById(UUID id);
    boolean appropriateUser(UUID idUser, UserPermission permission);
    AnonymousUserDto openAnonymousSession();
    boolean closeAnonymousSession(AnonymousUserDto userDto);
}
