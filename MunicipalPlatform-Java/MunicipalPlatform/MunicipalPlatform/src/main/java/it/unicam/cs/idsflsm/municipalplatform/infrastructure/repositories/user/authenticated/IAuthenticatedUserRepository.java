package it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.user.authenticated;
import it.unicam.cs.idsflsm.municipalplatform.application.criterias.user.authenticated.AuthenticatedUserCriteria;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserRole;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.GenericRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface IAuthenticatedUserRepository extends GenericRepository<AuthenticatedUser, UUID> {
    default List<AuthenticatedUser> findByUsername(String username) {
        return findByPredicate(AuthenticatedUserCriteria.hasUsername(username));
    }
    default List<AuthenticatedUser> findByName(String name) {
        return findByPredicate(AuthenticatedUserCriteria.hasName(name));
    }
    default List<AuthenticatedUser> findBySurname(String surname) {
        return findByPredicate(AuthenticatedUserCriteria.hasSurname(surname));
    }
    default List<AuthenticatedUser> findByRole(UserRole role) {
        return findByPredicate(AuthenticatedUserCriteria.hasRole(role));
    }
}
