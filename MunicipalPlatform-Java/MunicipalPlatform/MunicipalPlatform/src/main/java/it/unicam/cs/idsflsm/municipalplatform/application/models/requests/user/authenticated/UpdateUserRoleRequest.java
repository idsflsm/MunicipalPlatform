package it.unicam.cs.idsflsm.municipalplatform.application.models.requests.user.authenticated;

import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.validators.ValidUsername;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRoleRequest {
    @NotNull
    @NotBlank
    @ValidUsername
    private String email;
    @NotNull
    private boolean accept;
}
