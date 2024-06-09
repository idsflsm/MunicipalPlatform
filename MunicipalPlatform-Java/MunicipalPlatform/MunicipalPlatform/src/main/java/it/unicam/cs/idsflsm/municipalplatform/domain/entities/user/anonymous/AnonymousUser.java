package it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.anonymous;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Represents an anonymous user on the platform, it contains only the unique identifier
 * and the associated role
 */
@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "anonymous_user")
public abstract class AnonymousUser implements IAnonymousUser {
    /**
     * The unique identifier of the anonymous user
     */
    @Id
    // @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;
    /**
     * The role of the anonymous user
     */
    @Enumerated(EnumType.STRING)
    private final UserRole role = UserRole.TOURIST;
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
