package it.unicam.cs.idsflsm.municipalplatform.application.models.requests.attachment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
public class UpdateAttachmentRequest extends ModifyAttachmentRequest {
    public UpdateAttachmentRequest() {
    }
    public UpdateAttachmentRequest(@NotNull UUID idUser, @NotNull @NotBlank String name, @NotNull @NotBlank String description, @NotNull @NotBlank String author, @NotNull @NotBlank String expiryDate) {
        super(idUser, name, description, author, expiryDate);
    }
}
