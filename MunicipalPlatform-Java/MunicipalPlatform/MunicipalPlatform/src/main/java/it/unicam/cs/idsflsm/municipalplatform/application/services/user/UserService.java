package it.unicam.cs.idsflsm.municipalplatform.application.services.user;
import it.unicam.cs.idsflsm.municipalplatform.application.commands.user.authenticated.RemoveRoleCommand;
import it.unicam.cs.idsflsm.municipalplatform.application.commands.user.authenticated.UpdateRoleCommand;
import it.unicam.cs.idsflsm.municipalplatform.application.configurations.PasswordEncoder;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.user.IUserService;
import it.unicam.cs.idsflsm.municipalplatform.application.factories.user.authenticated.AuthenticatedUserBuilderFactory;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.user.RoleRequestMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.user.anonymous.TouristMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.user.authenticated.GenericAuthenticatedUserMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.RoleRequestDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.anonymous.AnonymousUserDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedUserDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.user.authenticated.LoginRequest;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.user.authenticated.RegisterRequest;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.RoleRequest;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.anonymous.Tourist;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserPermission;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserRole;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.user.anonymous.IAnonymousUserRepository;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.user.IRoleRequestRepository;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.user.authenticated.IAuthenticatedUserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
/**
 * Service class for the AuthenticatedUserRepository, AnonymousUserRepository and RoleRequestRepository.
 * It provides methods to manipulate persistent authenticated and anonymous users, and their requested roles,
 * in the database
 */
@Service
@Transactional
@AllArgsConstructor(onConstructor_ = @Autowired)
public class UserService implements IUserService {
    /**
     * The repository for AuthenticatedUser entity
     */
    private final IAuthenticatedUserRepository _authenticatedUserRepository;
    /**
     * The repository for AnonymousUser entity
     */
    private final IAnonymousUserRepository _anonymousUserRepository;
    /**
     * The repository for RoleRequest entity
     */
    private final IRoleRequestRepository _roleRequestRepository;
    /**
     * The password encoder for encoding user passwords
     */
    private final PasswordEncoder _passwordEncoder;
    @Override
    public void saveInRepository(AuthenticatedUser authenticatedUser) {
        _authenticatedUserRepository.save(authenticatedUser);
    }
    @Override
    public AuthenticatedUserDto register(RegisterRequest request) {
        var existingUser = findByUsername(request.getEmail());
        if (existingUser == null) {
            AuthenticatedUserBuilderFactory factory = new AuthenticatedUserBuilderFactory();
            var builder = factory.createAuthenticatedUserBuilder(UserRole.AUTHENTICATED_TOURIST);
            builder.setUsername(request.getEmail());
            builder.setPassword(_passwordEncoder.encodePassword(request.getPassword()));
            builder.setName(request.getName());
            builder.setSurname(request.getSurname());
            builder.setRole(UserRole.AUTHENTICATED_TOURIST);
            var user = builder.build();
            user.setId(UUID.randomUUID());
            user = _authenticatedUserRepository.save(user);
            return GenericAuthenticatedUserMapper.toDto(user, true);
        }
        return null;
    }
    @Override
    public AuthenticatedUserDto login(LoginRequest request) {
        List<AuthenticatedUser> users = _authenticatedUserRepository.findByUsername(request.getEmail());
        if (!users.isEmpty()) {
            AuthenticatedUser user = users.get(0);
            if (_passwordEncoder.verifyPassword(request.getPassword(), user.getPassword())) {
                return GenericAuthenticatedUserMapper.toDto(user, true);
            }
            return null;
        }
        return null;
    }
    @Override
    public RoleRequestDto sendRequestedRole(RoleRequestDto requestDto) {
        if (!_authenticatedUserRepository.findByUsername(requestDto.getUsername()).isEmpty()) {
            RoleRequest roleRequest = RoleRequestMapper.toEntity(requestDto);
            _roleRequestRepository.save(roleRequest);
            return RoleRequestMapper.toDto(roleRequest);
        }
        return null;
    }
    @Override
    public List<RoleRequestDto> receiveRequestedRoles(Predicate<RoleRequest> predicate) {
        List<RoleRequest> roleRequests = _roleRequestRepository.findAll().stream().filter(predicate).toList();
        if (!roleRequests.isEmpty()) {
            return RoleRequestMapper.toDto(roleRequests);
        }
        return new ArrayList<>();
    }
    @Override
    public AuthenticatedUserDto updateUserRole(UUID idUser, boolean accept) {
        AuthenticatedUser authenticatedUser = _authenticatedUserRepository.findById(idUser).orElse(null);
        RoleRequest roleRequest = (authenticatedUser != null) ? _roleRequestRepository.findById(authenticatedUser.getUsername()).orElse(null) : null;
        if (authenticatedUser != null && roleRequest != null) {
            if (accept) {
                _authenticatedUserRepository.delete(authenticatedUser);
                var newUser = swapRoles(authenticatedUser, roleRequest);
                _authenticatedUserRepository.save(newUser);
            }
            _roleRequestRepository.delete(roleRequest);
            return GenericAuthenticatedUserMapper.toDto(authenticatedUser, true);
        }
        return null;
    }
    private AuthenticatedUser swapRoles(AuthenticatedUser authenticatedUser, RoleRequest roleRequest) {
        AuthenticatedUserBuilderFactory factory = new AuthenticatedUserBuilderFactory();
        var builder = (roleRequest != null)
                ? factory.createAuthenticatedUserBuilder(roleRequest.getRole())
                : factory.createAuthenticatedUserBuilder(UserRole.AUTHENTICATED_TOURIST);
        builder.setUsername(authenticatedUser.getUsername());
        builder.setPassword(authenticatedUser.getPassword());
        builder.setName(authenticatedUser.getName());
        builder.setSurname(authenticatedUser.getSurname());
        builder.setPois(authenticatedUser.getPois());
        builder.setItineraries(authenticatedUser.getItineraries());
        var user = builder.build();
        user.setId(authenticatedUser.getId());
        if (roleRequest != null) {
            user.setCommand(new UpdateRoleCommand(user, roleRequest.getRole()));
        } else {
            user.setCommand(new RemoveRoleCommand(user));
        }
        user.executeCommand();
        return user;
    }
    @Override
    public AuthenticatedUserDto removeUserRole(UUID idUser) {
        AuthenticatedUser authenticatedUser = _authenticatedUserRepository.findById(idUser).orElse(null);
        if (authenticatedUser != null && !authenticatedUser.getRole().equals(UserRole.ADMINISTRATOR)) {
            authenticatedUser.setCommand(new RemoveRoleCommand(authenticatedUser));
            var newUser = swapRoles(authenticatedUser, null);
            _authenticatedUserRepository.delete(authenticatedUser);
            _authenticatedUserRepository.save(newUser);
            return GenericAuthenticatedUserMapper.toDto(newUser, true);
        } else {
            return null;
        }
    }
    @Override
    public AuthenticatedUserDto findByUsername(String username) {
        return GenericAuthenticatedUserMapper.toDto(_authenticatedUserRepository.findAll()
                .stream()
                .filter(authenticatedUser -> authenticatedUser.getUsername().equals(username))
                .findFirst()
                .orElse(null), true);
    }
    @Override
    public AuthenticatedUserDto findById(UUID id) {
        return GenericAuthenticatedUserMapper.toDto(_authenticatedUserRepository.findAll()
                .stream()
                .filter(authenticatedUser -> authenticatedUser.getId().equals(id))
                .findFirst()
                .orElse(null), true);
    }
    @Override
    public boolean appropriateUser(UUID idUser, UserPermission permission) {
        var authenticatedUser = this.findById(idUser);
        if (authenticatedUser != null) {
            return authenticatedUser.getRole().getPermissions().contains(permission);
        } else {
            var anonymousUser = _anonymousUserRepository.findById(idUser).orElse(null);
            if (anonymousUser != null) {
                return anonymousUser.getRole().getPermissions().contains(permission);
            }
            return false;
        }
    }
    @Override
    public AnonymousUserDto openAnonymousSession() {
        Tourist tourist = new Tourist(UUID.randomUUID());
        if (!_anonymousUserRepository.existsById(tourist.getId())) {
            _anonymousUserRepository.save(tourist);
            return TouristMapper.toDto(tourist);
        }
        return null;
    }
    @Override
    public boolean closeAnonymousSession(AnonymousUserDto userDto) {
        if (_anonymousUserRepository.existsById(userDto.getId())) {
            _anonymousUserRepository.deleteById(userDto.getId());
            return true;
        }
        return false;
    }
}
