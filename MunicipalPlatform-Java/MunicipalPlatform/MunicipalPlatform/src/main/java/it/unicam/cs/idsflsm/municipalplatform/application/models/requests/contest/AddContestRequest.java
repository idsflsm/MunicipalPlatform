package it.unicam.cs.idsflsm.municipalplatform.application.models.requests.contest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
public class AddContestRequest extends ModifyContestRequest {
    public AddContestRequest() {
    }
    public AddContestRequest(@NotNull UUID idUser, @NotNull @NotBlank String name, @NotNull @NotBlank String author, @NotNull @NotBlank String description, @NotNull @NotBlank String expiryDate) {
        super(idUser, name, author, description, expiryDate);
    }
}
