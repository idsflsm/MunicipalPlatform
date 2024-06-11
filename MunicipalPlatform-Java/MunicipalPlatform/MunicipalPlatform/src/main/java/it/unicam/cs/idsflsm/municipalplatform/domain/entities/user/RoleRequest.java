package it.unicam.cs.idsflsm.municipalplatform.domain.entities.user;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
/**
 * Represents the role change request that an authenticated user can make.
 * It contains user’s username and the role he requested
 */
@Entity
@Getter
@Setter
@Table(name = "role_request")
public class RoleRequest {
    /**
     * The unique identifier of the authenticated user
     */
    @Column(name = "id_user", nullable = false, unique = true, updatable = false)
    private UUID idUser;
    /**
     * The username of the authenticated user that made the request
     */
    @Id
    @Column(name = "username", nullable = false, unique = true, updatable = false)
    private String username;
    /**
     * The role requested by the authenticated user
     */
    @Enumerated(EnumType.STRING)
    private UserRole role;
    public RoleRequest() {
    }
    public RoleRequest(UUID idUser, String username, UserRole role) {
        this.idUser = idUser;
        this.username = username;
        this.role = role;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        RoleRequest other = (RoleRequest) obj;
        return other.getIdUser().equals(this.getIdUser())
                || other.getUsername().equals(this.getUsername());
    }
}
