package it.unicam.cs.idsflsm.municipalplatform.application.handlers.content.itinerary;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.handlers.content.itinerary.IItineraryHandler;
import it.unicam.cs.idsflsm.municipalplatform.application.factories.content.itinerary.ItineraryBuilderFactory;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.attachment.GenericAttachmentMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.poi.GenericPOIMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.contest.ContributionMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.user.authenticated.GenericAuthenticatedUserMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.ItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserPermission;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * Handler class for Itinerary modification
 */
@Component
@AllArgsConstructor(onConstructor_ = @Autowired)
public class ItineraryHandler implements IItineraryHandler {
    /**
     * The factory responsible for creating Itinerary builders
     */
    private final ItineraryBuilderFactory _itineraryBuilderFactory;
    @Override
    public Itinerary modifyItinerary(ItineraryDto itineraryDto, UserPermission permission) {
        var builder = _itineraryBuilderFactory.createItineraryBuilder(permission);
        builder.setName(itineraryDto.getName());
        builder.setCoordinates(itineraryDto.getCoordinates());
        builder.setDescription(itineraryDto.getDescription());
        builder.setAuthor(itineraryDto.getAuthor());
        builder.setCreationDate(itineraryDto.getCreationDate());
        builder.setExpiryDate(itineraryDto.getExpiryDate());
        builder.setContentState(itineraryDto.getState());
        builder.setItineraryPois(GenericPOIMapper.toEntity(itineraryDto.getItineraryPois(), true));
        builder.setUsers(GenericAuthenticatedUserMapper.toEntity(itineraryDto.getUsers(), true));
        builder.setAttachments(GenericAttachmentMapper.toEntity(itineraryDto.getAttachments(), true));
        builder.setContribution(ContributionMapper.toEntity(itineraryDto.getContribution(), true));
        var itinerary = builder.build();
        itinerary.setId(itineraryDto.getId());
        return itinerary;
    }
}
