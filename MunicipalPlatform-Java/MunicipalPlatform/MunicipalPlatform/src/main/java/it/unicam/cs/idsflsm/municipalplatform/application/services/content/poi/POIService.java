package it.unicam.cs.idsflsm.municipalplatform.application.services.content.poi;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.handlers.attachment.IAttachmentHandler;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.handlers.content.poi.IPOIHandler;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.content.poi.IPOIService;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.attachment.GenericAttachmentMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.poi.GenericPOIMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.user.authenticated.GenericAuthenticatedUserMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.POIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedUserDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserPermission;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.content.itinerary.IItineraryRepository;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.content.poi.IPOIRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;
/**
 * Service class for the POIRepository. It provides methods to manipulate persistent
 * POIs in the database
 */
@Service
@Transactional
@AllArgsConstructor(onConstructor_ = @Autowired)
public class POIService implements IPOIService {
    /**
     * The repository for POI entity
     */
    private final IPOIRepository _poiRepository;
    /**
     * The repository for Itinerary entity
     */
    private final IItineraryRepository _itineraryRepository;
    /**
     * The handler for POI entity
     */
    private final IPOIHandler _poiHandler;
    /**
     * The handler for Attachment entity
     */
    private final IAttachmentHandler _attachmentHandler;
    @Override
    public void saveInRepository(POI poi) {
        _poiRepository.save(poi);
    }
    @Override
    public List<POIDto> getPOIs(Optional<Predicate<POI>> predicate) {
        Stream<POI> poiStream = _poiRepository.findAll().stream();
        List<POI> pois = predicate.map(poiPredicate -> poiStream
                        .filter(poiPredicate)
                        .toList())
                .orElseGet(poiStream::toList);
        if (!pois.isEmpty()) {
            return GenericPOIMapper.toDto(pois, true);
        }
        return new ArrayList<>();
    }
    @Override
    public POIDto getPOIById(UUID id) {
        POI poi = _poiRepository.findById(id).orElse(null);
        if (poi != null) {
            return GenericPOIMapper.toDto(poi, true);
        }
        return null;
    }
    @Override
    public POIDto addPOI(POIDto poiDto, UserPermission permission) {
        POI poi = _poiHandler.modifyPOI(poiDto, permission);
        poi.getPoiItineraries().forEach(itinerary -> {
            itinerary.getItineraryPois().add(poi);
        });
        _poiRepository.save(poi);
        return GenericPOIMapper.toDto(poi, true);
    }
    //    TODO : FOR TESTING PURPOSES ONLY
    @Override
    public POIDto deletePOIById(UUID id) {
        POI poi = _poiRepository.findById(id).orElse(null);
        if (poi != null) {
            List<Itinerary> poiItineraries = poi.getPoiItineraries();
            poi.detachFromEntities();
            _poiRepository.delete(poi);
            poiItineraries.forEach(itinerary -> {
                if (itinerary.getItineraryPois().size() < 2) {
                    itinerary.detachFromEntities();
                    _itineraryRepository.delete(itinerary);
                }
            });
            return GenericPOIMapper.toDto(poi, true);
        }
        return null;
    }
    @Override
    public POIDto updatePOI(POIDto poiDto) {
        POI poi = _poiHandler.modifyPOI(poiDto, UserPermission.CURATOR_CONTENT_UPDATE);
        poi.getPoiItineraries().forEach(itinerary -> {
            itinerary.getItineraryPois().add(poi);
        });
        _poiRepository.save(poi);
        return GenericPOIMapper.toDto(poi, true);
    }
    @Override
    public POIDto validatePOI(Predicate<POI> predicate, boolean validate) {
        List<POI> pois = _poiRepository.findAll().stream().filter(predicate).toList();
        if (!pois.isEmpty()) {
            POI poi = pois.get(0);
            ContentState newState = (validate) ? ContentState.UPLOADABLE : ContentState.REMOVABLE;
            poi.setState(newState);
            _poiRepository.save(poi);
            return GenericPOIMapper.toDto(poi, true);
        }
        return null;
    }
    @Override
    public POIDto savePOI(UUID id, AuthenticatedUserDto userDto) {
        AuthenticatedUser user = GenericAuthenticatedUserMapper.toEntity(userDto, true);
        POI poi = _poiRepository.findById(id).orElse(null);
        if (user != null && poi != null && poi.getState().equals(ContentState.UPLOADED)) {
            user.getPois().add(poi);
            poi.getUsers().add(user);
            _poiRepository.save(poi);
            return GenericPOIMapper.toDto(poi, true);
        }
        return null;
    }
    @Override
    public POIDto uploadPOIById(UUID id) {
        POI poi = _poiRepository.findById(id).orElse(null);
        if (poi != null && poi.getState().equals(ContentState.UPLOADABLE)) {
            poi.setState(ContentState.UPLOADED);
            _poiRepository.save(poi);
            return GenericPOIMapper.toDto(poi, true);
        }
        return null;
    }
    @Override
    public AttachmentDto addAttachment(AttachmentDto attachmentDto, UserPermission permission, Predicate<POI> predicate) {
        List<POI> pois = _poiRepository.findAll().stream().filter(predicate).toList();
        if (!pois.isEmpty()) {
            POI poi = pois.get(0);
            Attachment attachment = _attachmentHandler.modifyAttachment(attachmentDto, permission);
            poi.getAttachments().add(attachment);
            attachment.setPoi(poi);
            _poiRepository.save(poi);
            return GenericAttachmentMapper.toDto(attachment, true);
        }
        return null;
    }
}

