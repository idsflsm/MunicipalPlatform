package it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.anonymous;
import java.util.UUID;
public class Tourist extends AnonymousUser {
    public Tourist() {
    }
    public Tourist(UUID id) {
        super(id);
    }
}
