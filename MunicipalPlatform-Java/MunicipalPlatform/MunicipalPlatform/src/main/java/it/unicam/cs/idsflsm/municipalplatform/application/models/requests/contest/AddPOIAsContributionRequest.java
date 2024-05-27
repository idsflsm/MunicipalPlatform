package it.unicam.cs.idsflsm.municipalplatform.application.models.requests.contest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
public class AddPOIAsContributionRequest extends ModifyContentAsContributionRequest {
    public AddPOIAsContributionRequest() {
    }
    public AddPOIAsContributionRequest(@NotNull UUID idUser, @NotNull @NotBlank String name, @NotNull @NotBlank String latitude, @NotNull @NotBlank String longitude, @NotNull @NotBlank String description, @NotNull @NotBlank String author, @NotNull @NotBlank String expiryDate) {
        super(idUser, name, latitude, longitude, description, author, expiryDate);
    }
}
