package it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.anonymous;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserRole;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.UUID;
/**
 * Represents an anonymous user on the platform, acting as tourist
 */
@Entity
@DiscriminatorValue("tourist")
public class Tourist extends AnonymousUser {
    public Tourist() {
    }
    public Tourist(UUID id) {
        super(id);
    }
}
