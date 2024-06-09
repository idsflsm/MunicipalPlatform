package it.unicam.cs.idsflsm.municipalplatform.application.abstractions.handlers.content.poi;

import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.POIDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserPermission;

public interface IPOIHandler {
    /**
     * Method for modifying a POI entity, based on the provided
     * POI DTO and user permission
     * @param poiDto the POI DTO containing new information
     * @param permission the user permission determining the correct builder instance
     * @return the modified POI entity
     */
    POI modifyPOI(POIDto poiDto, UserPermission permission);
}
