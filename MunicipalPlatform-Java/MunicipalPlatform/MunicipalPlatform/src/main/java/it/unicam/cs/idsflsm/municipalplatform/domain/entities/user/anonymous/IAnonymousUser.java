package it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.anonymous;
import java.util.UUID;

/**
 * Represents an anonymous user on the platform. Provides methods to get and set
 * the unique identifier
 */
public interface IAnonymousUser {
    UUID getId();
    void setId(UUID id);
}
