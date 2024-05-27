package it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.user;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.RoleRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRequestRepository extends JpaRepository<RoleRequest, String> {
}
