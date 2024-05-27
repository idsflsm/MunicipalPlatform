package it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.user.authenticated;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.GenericRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface IAuthenticatedUserRepository extends GenericRepository<AuthenticatedUser, UUID> {
    default AuthenticatedUser findByUsername(String username) {
        return findByPredicate(authenticatedUser -> authenticatedUser.getUsername().equals(username)).get(0);
    }
}
