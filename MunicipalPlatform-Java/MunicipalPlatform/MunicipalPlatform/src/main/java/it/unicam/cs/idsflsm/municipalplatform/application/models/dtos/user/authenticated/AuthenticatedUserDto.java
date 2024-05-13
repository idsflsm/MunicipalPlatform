package it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
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
public abstract class AuthenticatedUserDto {
    private UUID id = UUID.randomUUID();
    private String username;
    @JsonIgnore
    private String password;
    private String name;
    private String surname;
    @JsonManagedReference
    private List<POI> pois;
    @JsonManagedReference
    private List<Itinerary> itineraries;
}
