package it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.user;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.RoleRequest;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.GenericRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRequestRepository extends GenericRepository<RoleRequest, String> {
}
