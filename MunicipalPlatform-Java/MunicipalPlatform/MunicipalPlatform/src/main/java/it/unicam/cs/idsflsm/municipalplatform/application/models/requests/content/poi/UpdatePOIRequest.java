package it.unicam.cs.idsflsm.municipalplatform.application.models.requests.content.poi;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
public class UpdatePOIRequest extends ModifyPOIRequest {
    public UpdatePOIRequest() {
    }
    public UpdatePOIRequest(@NotNull UUID idUser, @NotNull @NotBlank String name, @NotNull @NotBlank String latitude, @NotNull @NotBlank String longitude, @NotNull @NotBlank String description, @NotNull @NotBlank String author, @NotNull @NotBlank String expiryDate) {
        super(idUser, name, latitude, longitude, description, author, expiryDate);
    }
}
