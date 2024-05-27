package it.unicam.cs.idsflsm.municipalplatform.application.models.requests.attachment;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.validators.ValidUUID;
import jakarta.validation.constraints.NotEmpty;
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
public class ValidatePendingAttachmentRequest {
    @NotNull
    @ValidUUID
    private UUID idUser;
    @NotNull
    @NotEmpty
    private boolean validate;
}
