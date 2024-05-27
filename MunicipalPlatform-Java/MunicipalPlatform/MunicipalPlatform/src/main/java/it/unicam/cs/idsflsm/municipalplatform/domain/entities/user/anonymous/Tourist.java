package it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.anonymous;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.UUID;
@Entity
@DiscriminatorValue("tourist")
public class Tourist extends AnonymousUser {
    public Tourist() {
    }
    public Tourist(UUID id) {
        super(id);
    }
}
