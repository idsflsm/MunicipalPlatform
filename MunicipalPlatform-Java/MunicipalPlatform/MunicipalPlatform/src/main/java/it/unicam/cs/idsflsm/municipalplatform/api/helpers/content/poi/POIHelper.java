package it.unicam.cs.idsflsm.municipalplatform.api.helpers.content.poi;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.POIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.content.poi.ModifyPOIRequest;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Coordinates;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
/**
 * Component class providing helper methods for modifying POI fields
 */
@Component
public class POIHelper {
    /**
     * Method for modifying the POI DTO configuration, based on the provided request
     *
     * @param <T>     type of POIDto
     * @param <S>     type of ModifyPOIRequest
     * @param poiDto  the POIDto instance to be modified
     * @param request the ModifyPOIRequest instance containing the new configuration
     * @return the modified POI DTO
     */
    public <T extends POIDto, S extends ModifyPOIRequest> T modifyPoiConfiguration(T poiDto, S request, ContentState state) {
        poiDto.setName(request.getName());
        poiDto.setCoordinates(Coordinates.fromStrings(request.getLatitude(), request.getLongitude()));
        poiDto.setDescription(request.getDescription());
        poiDto.setAuthor(request.getAuthor());
        poiDto.setCreationDate(Date.toDate(LocalDate.now()));
        poiDto.setExpiryDate(Date.fromString(request.getExpiryDate()));
        poiDto.setState(state);
        return poiDto;
    }
}
