package it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.anonymous;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
/**
 * Represents a DTO related to the entity AnonymousUser.
 * It contains all fields belonging to the entity
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class AnonymousUserDto {
    private UUID id = UUID.randomUUID();
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        AnonymousUserDto other = (AnonymousUserDto) obj;
        return other.getId().equals(this.getId());
    }
}
