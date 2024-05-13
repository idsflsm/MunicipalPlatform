package it.unicam.cs.idsflsm.municipalplatform.application.models.requests.report;

import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.validators.OnlyLettersString;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class ModifyReportRequest {
    @NotNull
    @NotBlank
    @OnlyLettersString
    private String motivation;
}
