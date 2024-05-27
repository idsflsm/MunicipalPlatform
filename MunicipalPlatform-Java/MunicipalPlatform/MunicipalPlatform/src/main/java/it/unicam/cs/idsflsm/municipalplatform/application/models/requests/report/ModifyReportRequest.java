package it.unicam.cs.idsflsm.municipalplatform.application.models.requests.report;

import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.validators.OnlyLettersString;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.validators.ValidUUID;
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
public abstract class ModifyReportRequest {
    @NotNull
    @ValidUUID
    private UUID idUser;

    @NotNull
    @NotBlank
    @OnlyLettersString
    private String motivation;
}
