package it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.anonymous;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "anonymous_user")
public abstract class AnonymousUser implements IAnonymousUser {
    @Id
    // @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;
    public AnonymousUser() {
    }
    public AnonymousUser(UUID id) {
        this.id = id;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        AnonymousUser other = (AnonymousUser) obj;
        return other.getId().equals(this.getId());
    }
}
