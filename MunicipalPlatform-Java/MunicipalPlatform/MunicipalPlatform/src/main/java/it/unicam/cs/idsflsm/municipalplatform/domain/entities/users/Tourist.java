package it.unicam.cs.idsflsm.municipalplatform.domain.entities.users;

import java.util.UUID;

public class Tourist extends AnonymousUser {
    @Override
    public UUID getId() {
        return super.id;
    }
}
