package it.unicam.cs.idsflsm.municipalplatform.domain.entities.user;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "role_request")
public class RoleRequest {
    @Id
    @Column(name = "username", nullable = false, unique = true, updatable = false)
    private String username;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    public RoleRequest() {
    }
    public RoleRequest(String username, UserRole role) {
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
        return other.getUsername().equals(this.getUsername());
    }
}
