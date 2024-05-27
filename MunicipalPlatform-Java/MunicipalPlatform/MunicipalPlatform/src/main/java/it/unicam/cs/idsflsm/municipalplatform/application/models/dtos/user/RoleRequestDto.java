package it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user;

import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequestDto {
    private String username;
    private UserRole role;
}
