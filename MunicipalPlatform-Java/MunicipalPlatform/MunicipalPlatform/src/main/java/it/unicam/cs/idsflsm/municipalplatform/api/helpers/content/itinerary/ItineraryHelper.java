package it.unicam.cs.idsflsm.municipalplatform.api.helpers.content.itinerary;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.content.poi.IPOIService;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.ItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.POIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.content.itinerary.ModifyItineraryRequest;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Coordinates;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;
/**
 * Component class providing helper methods for modifying Itinerary fields
 */
@Component
@AllArgsConstructor(onConstructor_ = @Autowired)
public class ItineraryHelper {
    /**
     * The service for POI manipulation
     */
    private final IPOIService _poiService;
    /**
     * Method for modifying the Itinerary DTO configuration, based on the provided request
     *
     * @param <T>          type of ItineraryDto
     * @param <S>          type of ModifyItineraryRequest
     * @param itineraryDto the ItineraryDto instance to be modified
     * @param request      the ModifyItineraryRequest instance containing the new configuration
     * @return the modified Itinerary DTO
     */
    public <T extends ItineraryDto, S extends ModifyItineraryRequest> T modifyItineraryConfiguration
    (T itineraryDto, S request, ContentState state) {
        itineraryDto.setName(request.getName());
        itineraryDto.setCoordinates(Coordinates.fromStrings(request.getLatitude(), request.getLongitude()));
        itineraryDto.setDescription(request.getDescription());
        itineraryDto.setAuthor(request.getAuthor());
        itineraryDto.setCreationDate(Date.toDate(LocalDate.now()));
        itineraryDto.setExpiryDate(Date.fromString(request.getExpiryDate()));
        itineraryDto.setState(state);
        for (UUID idPoi : request.getPois()) {
            POIDto poiDto = _poiService.getPOIById(idPoi);
            if (poiDto != null
                    && poiDto.getState().equals(ContentState.UPLOADED)
                    && !itineraryDto.getItineraryPois().contains(poiDto)) {
                itineraryDto.getItineraryPois().add(poiDto);
            }
        }
        return (itineraryDto.getItineraryPois().size() >= 2) ? itineraryDto : null;
    }
}
