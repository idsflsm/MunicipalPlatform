package it.unicam.cs.idsflsm.municipalplatform.application.models.requests.user;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.validators.OnlyLettersString;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.validators.ValidPassword;
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
public class RegisterRequest {
    @NotNull
    @NotBlank
    @OnlyLettersString
    private String name;
    @NotNull
    @NotBlank
    @OnlyLettersString
    private String surname;
    @NotNull
    @NotBlank
    @ValidUsername
    private String email;
    @NotNull
    @NotBlank
    @ValidPassword
    private String password;
}
