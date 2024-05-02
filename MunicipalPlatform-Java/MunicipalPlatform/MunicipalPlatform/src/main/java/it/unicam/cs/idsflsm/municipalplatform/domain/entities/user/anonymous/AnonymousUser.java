package it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.anonymous;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public abstract class AnonymousUser implements IAnonymousUser {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id = UUID.randomUUID();
    public AnonymousUser() {
    }
    public AnonymousUser(UUID id) {
        this.id = (id != null) ? id : UUID.randomUUID();
    }
}
