package it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.user;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.RoleRequest;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.GenericRepository;
import org.springframework.stereotype.Repository;
/**
 * Interface that represents the repository for RoleRequest entity
 */
@Repository
public interface IRoleRequestRepository extends GenericRepository<RoleRequest, String> {
}
