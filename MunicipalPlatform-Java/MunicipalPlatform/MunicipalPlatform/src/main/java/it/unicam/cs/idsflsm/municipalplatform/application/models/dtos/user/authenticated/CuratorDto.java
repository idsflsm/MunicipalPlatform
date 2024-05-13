package it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;

import java.util.List;
import java.util.UUID;
public class CuratorDto extends AuthenticatedUserDto {
    public CuratorDto() {
    }
    public CuratorDto(UUID id, String username, String password, String name, String surname, List<POI> pois, List<Itinerary> itineraries) {
        super(id, username, password, name, surname, pois, itineraries);
    }
}
