package it.unicam.cs.idsflsm.municipalplatform.application.models.requests.report;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
public class AddReportRequest extends ModifyReportRequest {
    public AddReportRequest() {
    }
    public AddReportRequest(@NotNull UUID idUser, @NotNull @NotBlank String motivation) {
        super(idUser, motivation);
    }
}
