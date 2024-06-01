package it.unicam.cs.idsflsm.municipalplatform.application.services.user;
import it.unicam.cs.idsflsm.municipalplatform.application.commands.user.IRoleCommand;
import it.unicam.cs.idsflsm.municipalplatform.application.commands.user.RemoveRoleCommand;
import it.unicam.cs.idsflsm.municipalplatform.application.commands.user.UpdateRoleCommand;
import it.unicam.cs.idsflsm.municipalplatform.application.configurations.PasswordEncoder;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.user.IUserService;
import it.unicam.cs.idsflsm.municipalplatform.application.factories.user.AuthenticatedUserBuilderFactory;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.user.RoleRequestMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.user.anonymous.TouristMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.user.authenticated.GenericAuthenticatedUserMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.RoleRequestDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.anonymous.AnonymousUserDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedUserDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.user.LoginRequest;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.user.RegisterRequest;
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

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

@Service
@Transactional
@AllArgsConstructor(onConstructor_ = @Autowired)
public class UserService implements IUserService {
    private final IAuthenticatedUserRepository _authenticatedUserRepository;
    private final IAnonymousUserRepository _anonymousUserRepository;
    private final IRoleRequestRepository _requestRoleRepository;
    private final PasswordEncoder _passwordEncoder;
    @Override
    public void saveInRepository(AuthenticatedUser authenticatedUser) {
        _authenticatedUserRepository.save(authenticatedUser);
    }
    @Override
    public AuthenticatedUserDto register(RegisterRequest request) {
        var existingUser = findByUsername(request.getEmail());
        if (existingUser == null) {
            // TODO : DO NOT FORGET
            AuthenticatedUserBuilderFactory factory = new AuthenticatedUserBuilderFactory();
            var builder = factory.createAuthenticatedUserBuilder(UserRole.ANIMATOR);
            builder.setUsername(request.getEmail());
            builder.setPassword(_passwordEncoder.encodePassword(request.getPassword()));
            builder.setName(request.getName());
            builder.setSurname(request.getSurname());
            builder.setRole(UserRole.ANIMATOR);
//            user.setRole(UserRole.AUTHENTICATED_TOURIST);
            var user = builder.build();
            user.setId(UUID.randomUUID());
            user = _authenticatedUserRepository.save(user);
            return GenericAuthenticatedUserMapper.toDto(user, true);
        } else {
            return null;
        }
    }
    @Override
    public AuthenticatedUserDto login(LoginRequest request) {
        var existingUser = _authenticatedUserRepository.findByUsername(request.getEmail());
        if (existingUser != null) {
            if (_passwordEncoder.verifyPassword(request.getPassword(), existingUser.getPassword())) {
                return GenericAuthenticatedUserMapper.toDto(existingUser, true);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
    @Override
    public RoleRequestDto sendRequestedRole(RoleRequestDto requestDto) {
        if (_authenticatedUserRepository.findByUsername(requestDto.getUsername()) != null) {
            RoleRequest roleRequest = RoleRequestMapper.toEntity(requestDto);
            _requestRoleRepository.save(roleRequest);
            return RoleRequestMapper.toDto(roleRequest);
        } else {
            return null;
        }
    }
    @Override
    public List<RoleRequestDto> receiveRequestedRoles(Predicate<RoleRequest> predicate) {
        return RoleRequestMapper.toDto(_requestRoleRepository.findAll().stream().filter(predicate).toList());
    }
    @Override
    public AuthenticatedUserDto updateUserRole(UUID idUser, boolean accept) {
        AuthenticatedUser authenticatedUser = _authenticatedUserRepository.findById(idUser).orElse(null);
        RoleRequest roleRequest = (authenticatedUser != null) ? _requestRoleRepository.findById(authenticatedUser.getUsername()).orElse(null) : null;
        if (authenticatedUser != null && roleRequest != null) {
            if (accept) {
                // authenticatedUser.setCommand(new UpdateRoleCommand(authenticatedUser, roleRequest.getRole()));
                var newUser = swapRoles(authenticatedUser, roleRequest);
                _authenticatedUserRepository.delete(authenticatedUser);
                _authenticatedUserRepository.save(newUser);
            }
            _requestRoleRepository.delete(roleRequest);
            return GenericAuthenticatedUserMapper.toDto(authenticatedUser, true);
        } else {
            return null;
        }
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
//         TODO : CHEATED HERE!
        // builder.setRole(authenticatedUser.getRole());
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
        if (authenticatedUser != null) {
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
    public RoleRequestDto findRoleRequestByUsername(String username) {
        return RoleRequestMapper.toDto(_requestRoleRepository.findAll()
                .stream()
                .filter(roleRequest -> roleRequest.getUsername().equals(username))
                .findFirst()
                .orElse(null));
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
            return anonymousUser != null;
        }
    }

    @Override
    public AnonymousUserDto openAnonymousSession() {
        Tourist tourist = new Tourist(UUID.randomUUID());
        if (!_anonymousUserRepository.existsById(tourist.getId())) {
            _anonymousUserRepository.save(tourist);
            return TouristMapper.toDto(tourist);
        } else {
            return null;
        }
    }
    @Override
    public boolean closeAnonymousSession(AnonymousUserDto userDto) {
        if (_anonymousUserRepository.existsById(userDto.getId())) {
            _anonymousUserRepository.deleteById(userDto.getId());
            return true;
        } else {
            return false;
        }
    }
}
