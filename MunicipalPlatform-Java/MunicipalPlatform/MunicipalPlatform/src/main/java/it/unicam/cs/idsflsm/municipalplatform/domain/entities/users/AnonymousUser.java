package it.unicam.cs.idsflsm.municipalplatform.domain.entities.users;

import java.util.UUID;

public abstract class AnonymousUser implements IAnonymousUser {
    protected final UUID id = UUID.randomUUID();
}
