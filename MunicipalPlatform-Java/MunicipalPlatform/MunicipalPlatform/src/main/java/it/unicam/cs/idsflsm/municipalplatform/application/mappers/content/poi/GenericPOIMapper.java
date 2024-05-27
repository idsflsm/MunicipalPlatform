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
public class GenericPOIMapper {
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
    public static List<POIDto> toDto(List<POI> pois, boolean includeRelativeEntities) {
        if (pois != null) {
            return pois.stream()
                    .map(poi -> toDto(poi, includeRelativeEntities))
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }
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
