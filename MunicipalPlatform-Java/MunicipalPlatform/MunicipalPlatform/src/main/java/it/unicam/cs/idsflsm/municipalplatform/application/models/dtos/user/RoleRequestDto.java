package it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
/**
 * Represents a DTO related to the entity RoleRequest.
 * It contains all fields belonging to the entity
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequestDto {
    private UUID idUser;
    private String username;
    private UserRole role;
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        RoleRequestDto other = (RoleRequestDto) obj;
        return other.getIdUser().equals(this.getIdUser())
                || other.getUsername().equals(this.getUsername());
    }
}
