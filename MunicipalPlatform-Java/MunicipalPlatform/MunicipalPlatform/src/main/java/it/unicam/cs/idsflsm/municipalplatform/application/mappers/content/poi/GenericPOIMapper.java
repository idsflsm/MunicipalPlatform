package it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.poi;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.ItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.AuthorizedPOIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.POIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.PendingPOIDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.AuthorizedPOI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.PendingPOI;

import java.util.List;
import java.util.stream.Collectors;
/**
 * Utility generic class for mapping between POI and POIDto
 */
public class GenericPOIMapper {
    /**
     * Converts a POI entity to a POI DTO
     * @param poi the POI entity to be converted
     * @param includeRelativeEntities flag indicating whether to map the sub-entities
     * @return the corresponding POIDto if poi in the parameter is not null, null otherwise
     */
    public static POIDto toDto(POI poi, boolean includeRelativeEntities) {
        if (poi != null) {
            if (poi instanceof PendingPOI pendingPOI) {
                return PendingPOIMapper.toDto(pendingPOI, includeRelativeEntities);
            } else if (poi instanceof AuthorizedPOI authorizedPOI) {
                return AuthorizedPOIMapper.toDto(authorizedPOI, includeRelativeEntities);
            }
        }
        return null;
    }
    /**
     * Converts a POI DTO to a POI entity
     * @param poiDto the POI DTO to be converted
     * @param includeRelativeEntities flag indicating whether to map the sub-DTOs
     * @return the corresponding POI if the poiDto in the parameter is not null, null otherwise
     */
    public static POI toEntity(POIDto poiDto, boolean includeRelativeEntities) {
        if (poiDto != null) {
            if (poiDto instanceof PendingPOIDto pendingPOIDto) {
                return PendingPOIMapper.toEntity(pendingPOIDto, includeRelativeEntities);
            } else if (poiDto instanceof AuthorizedPOIDto authorizedPOIDto) {
                return AuthorizedPOIMapper.toEntity(authorizedPOIDto, includeRelativeEntities);
            }
        }
        return null;
    }
    /**
     * Converts a list of POI entities to a list of POI DTOs
     * @param pois the list of POI entities to be converted
     * @param includeRelativeEntities flag indicating whether to map the sub-entities
     * @return the corresponding list of POIDto if the list in the parameter is not null, null otherwise
     */
    public static List<POIDto> toDto(List<POI> pois, boolean includeRelativeEntities) {
        if (pois != null) {
            return pois.stream()
                    .map(poi -> toDto(poi, includeRelativeEntities))
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }
    /**
     * Converts a list of POI DTOs to a list of POI entities
     * @param poiDtos the list of POI DTOs to be converted
     * @param includeRelativeEntities flag indicating whether to map the sub-DTOs
     * @return the corresponding list of POI if the list in the parameter is not null, null otherwise
     */
    public static List<POI> toEntity(List<POIDto> poiDtos, boolean includeRelativeEntities) {
        if (poiDtos != null) {
            return poiDtos.stream()
                    .map(poiDto -> toEntity(poiDto, includeRelativeEntities))
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }
}
