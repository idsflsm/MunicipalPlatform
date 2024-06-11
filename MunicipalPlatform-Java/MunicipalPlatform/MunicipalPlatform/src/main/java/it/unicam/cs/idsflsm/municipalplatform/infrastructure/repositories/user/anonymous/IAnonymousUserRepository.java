package it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.user.anonymous;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.anonymous.AnonymousUser;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.GenericRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
/**
 * Interface that represents the repository for AnonymousUser entity
 */
@Repository
public interface IAnonymousUserRepository extends GenericRepository<AnonymousUser, UUID> {
}
