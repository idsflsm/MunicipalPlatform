package it.unicam.cs.idsflsm.municipalplatform.application.models.requests.content.itinerary;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.validators.ValidDateFormat;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.validators.OnlyLettersString;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.validators.ValidCoordinate;
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
public abstract class ModifyItineraryRequest {
    @NotNull
    @NotBlank
    @OnlyLettersString
    private String name;
    @NotNull
    @NotBlank
    @ValidCoordinate
    private String latitude;
    @NotNull
    @NotBlank
    @ValidCoordinate
    private String longitude;
    @NotNull
    @NotBlank
    @OnlyLettersString
    private String description;
    @NotNull
    @NotBlank
    @OnlyLettersString
    private String author;
    @NotNull
    @NotBlank
    @ValidDateFormat
    private String expiryDate;
}
