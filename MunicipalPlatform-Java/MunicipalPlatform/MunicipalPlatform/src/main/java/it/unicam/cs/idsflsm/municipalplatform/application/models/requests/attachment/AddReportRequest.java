package it.unicam.cs.idsflsm.municipalplatform.application.models.requests.attachment;
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
public class AddReportRequest {
    @NotNull
    @ValidUUID
    private UUID idUser;

    @NotNull
    @NotBlank
    @OnlyLettersString
    private String motivation;
}
