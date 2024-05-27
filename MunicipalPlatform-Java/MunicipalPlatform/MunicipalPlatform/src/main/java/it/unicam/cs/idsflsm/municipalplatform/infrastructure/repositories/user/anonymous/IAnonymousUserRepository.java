package it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.user.anonymous;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.anonymous.AnonymousUser;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.GenericRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IAnonymousUserRepository extends GenericRepository<AnonymousUser, UUID> {
}
