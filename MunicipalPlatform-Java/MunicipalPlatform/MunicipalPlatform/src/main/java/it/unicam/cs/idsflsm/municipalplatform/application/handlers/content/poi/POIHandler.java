package it.unicam.cs.idsflsm.municipalplatform.application.handlers.content.poi;

import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.handlers.content.poi.IPOIHandler;
import it.unicam.cs.idsflsm.municipalplatform.application.factories.content.poi.POIBuilderFactory;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.attachment.GenericAttachmentMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.itinerary.GenericItineraryMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.contest.ContributionMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.user.authenticated.GenericAuthenticatedUserMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.POIDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserPermission;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * Handler class for POI modification
 */
@Component
@AllArgsConstructor(onConstructor_ = @Autowired)
public class POIHandler implements IPOIHandler {
    /**
     * The factory responsible for creating POI builders
     */
    private final POIBuilderFactory _poiBuilderFactory;
    @Override
    public POI modifyPOI(POIDto poiDto, UserPermission permission) {
        var builder = _poiBuilderFactory.createPOIBuilder(permission);
        builder.setName(poiDto.getName());
        builder.setCoordinates(poiDto.getCoordinates());
        builder.setDescription(poiDto.getDescription());
        builder.setAuthor(poiDto.getAuthor());
        builder.setCreationDate(poiDto.getCreationDate());
        builder.setExpiryDate(poiDto.getExpiryDate());
        builder.setContentState(poiDto.getState());
        builder.setPoiItineraries(GenericItineraryMapper.toEntity(poiDto.getPoiItineraries(), true));
        builder.setUsers(GenericAuthenticatedUserMapper.toEntity(poiDto.getTourists(), true));
        builder.setAttachments(GenericAttachmentMapper.toEntity(poiDto.getAttachments(), true));
        builder.setContribution(ContributionMapper.toEntity(poiDto.getContribution(), true));
        var poi = builder.build();
        poi.setId(poiDto.getId());
        return poi;
    }
}
