package it.unicam.cs.idsflsm.municipalplatform.application.models.requests.contest;

import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.validators.OnlyLettersString;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.validators.ValidCoordinate;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.validators.ValidDateFormat;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.validators.ValidUUID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
@Getter
@Setter
public class AddItineraryAsContributionRequest extends ModifyContentAsContributionRequest {
    @NotNull
    private List<UUID> pois;
    public AddItineraryAsContributionRequest() {
    }
    public AddItineraryAsContributionRequest(@NotNull UUID idUser, @NotNull @NotBlank String name, @NotNull @NotBlank String latitude, @NotNull @NotBlank String longitude, @NotNull @NotBlank String description, @NotNull @NotBlank String author, @NotNull @NotBlank String expiryDate) {
        super(idUser, name, latitude, longitude, description, author, expiryDate);
    }
}
