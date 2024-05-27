package it.unicam.cs.idsflsm.municipalplatform.application.models.requests.content.itinerary;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.validators.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class ModifyItineraryRequest {
//    @NotNull
//    @NotBlank
//    @ValidUsername
//    private String username;
    @NotNull
    @ValidUUID
    private UUID idUser;

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
    @ValidDateFormat
    private String expiryDate;
    @NotNull
    private List<UUID> pois;
}
