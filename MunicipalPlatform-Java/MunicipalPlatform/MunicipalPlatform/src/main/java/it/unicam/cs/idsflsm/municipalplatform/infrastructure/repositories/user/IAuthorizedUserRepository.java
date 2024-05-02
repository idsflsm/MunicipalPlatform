package it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.user;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
public interface IAuthorizedUserRepository extends JpaRepository<AuthenticatedUser, UUID> {
}
