package it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.user;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.GenericRepository;

import java.util.List;
import java.util.UUID;
public interface IAuthenticatedUserRepository extends GenericRepository<AuthenticatedUser, UUID> {
}
