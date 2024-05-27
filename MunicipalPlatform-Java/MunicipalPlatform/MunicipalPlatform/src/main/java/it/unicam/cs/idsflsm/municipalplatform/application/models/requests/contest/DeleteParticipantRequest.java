package it.unicam.cs.idsflsm.municipalplatform.application.models.requests.contest;

import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.validators.OnlyLettersString;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.validators.ValidUUID;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.validators.ValidUsername;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteParticipantRequest {
    @NotNull
    @ValidUUID
    private UUID idUser;
    @NotNull
    @NotBlank
    @ValidUsername
    private String username;
    @NotNull
    @NotBlank
    @OnlyLettersString
    private String name;
    @NotNull
    @NotBlank
    @OnlyLettersString
    private String surname;
}
