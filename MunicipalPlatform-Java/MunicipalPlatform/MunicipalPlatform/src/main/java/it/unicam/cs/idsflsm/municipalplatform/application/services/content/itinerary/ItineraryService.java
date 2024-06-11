package it.unicam.cs.idsflsm.municipalplatform.application.services.content.itinerary;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.handlers.attachment.IAttachmentHandler;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.handlers.content.itinerary.IItineraryHandler;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.content.itinerary.IItineraryService;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.attachment.GenericAttachmentMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.itinerary.GenericItineraryMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.user.authenticated.GenericAuthenticatedUserMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.ItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedUserDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserPermission;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.content.itinerary.IItineraryRepository;
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
 * Service class for the ItineraryRepository. It provides methods to manipulate persistent
 * itineraries in the database
 */
@Service
@Transactional
@AllArgsConstructor(onConstructor_ = @Autowired)
public class ItineraryService implements IItineraryService {
    /**
     * The repository for Itinerary entity
     */
    private final IItineraryRepository _itineraryRepository;
    /**
     * The handler for Itinerary entity
     */
    private final IItineraryHandler _itineraryHandler;
    /**
     * The handler for Attachment entity
     */
    private final IAttachmentHandler _attachmentHandler;
    @Override
    public void saveInRepository(Itinerary itinerary) {
        _itineraryRepository.save(itinerary);
    }
    @Override
    public List<ItineraryDto> getItineraries(Optional<Predicate<Itinerary>> predicate) {
        Stream<Itinerary> itineraryStream = _itineraryRepository.findAll().stream();
        List<Itinerary> itineraries = predicate.map(itineraryPredicate -> itineraryStream
                        .filter(itineraryPredicate)
                        .toList())
                .orElseGet(itineraryStream::toList);
        if (!itineraries.isEmpty()) {
            return GenericItineraryMapper.toDto(itineraries, true);
        }
        return new ArrayList<>();
    }
    @Override
    public ItineraryDto getItineraryById(UUID id) {
        Itinerary itinerary = _itineraryRepository.findById(id).orElse(null);
        if (itinerary != null) {
            return GenericItineraryMapper.toDto(itinerary, true);
        }
        return null;
    }
    @Override
    public ItineraryDto addItinerary(ItineraryDto itineraryDto, UserPermission permission) {
        Itinerary itinerary = _itineraryHandler.modifyItinerary(itineraryDto, permission);
        itinerary.getItineraryPois().forEach(poi -> {
            poi.getPoiItineraries().add(itinerary);
        });
        _itineraryRepository.save(itinerary);
        return GenericItineraryMapper.toDto(itinerary, true);
    }
    //    TODO : FOR TESTING PURPOSES ONLY
    @Override
    public ItineraryDto deleteItineraryById(UUID id) {
        Itinerary itinerary = _itineraryRepository.findById(id).orElse(null);
        if (itinerary != null) {
            itinerary.detachFromEntities();
            _itineraryRepository.delete(itinerary);
            return GenericItineraryMapper.toDto(itinerary, true);
        }
        return null;
    }
    @Override
    public ItineraryDto updateItinerary(ItineraryDto itineraryDto) {
        Itinerary itinerary = _itineraryHandler.modifyItinerary(itineraryDto, UserPermission.CURATOR_CONTENT_UPDATE);
        itinerary.getItineraryPois().forEach(poi -> {
            poi.getPoiItineraries().add(itinerary);
        });
        _itineraryRepository.save(itinerary);
        return GenericItineraryMapper.toDto(itinerary, true);
    }
    @Override
    public ItineraryDto validateItinerary(Predicate<Itinerary> predicate, boolean validate) {
        List<Itinerary> itineraries = _itineraryRepository.findAll().stream().filter(predicate).toList();
        if (!itineraries.isEmpty()) {
            Itinerary itinerary = itineraries.get(0);
            ContentState newState = (validate) ? ContentState.UPLOADABLE : ContentState.REMOVABLE;
            itinerary.setState(newState);
            _itineraryRepository.save(itinerary);
            return GenericItineraryMapper.toDto(itinerary, true);
        }
        return null;
    }
    @Override
    public ItineraryDto saveItinerary(UUID id, AuthenticatedUserDto userDto) {
        AuthenticatedUser user = GenericAuthenticatedUserMapper.toEntity(userDto, true);
        Itinerary itinerary = _itineraryRepository.findById(id).orElse(null);
        if (user != null && itinerary != null && itinerary.getState().equals(ContentState.UPLOADED)) {
            user.getItineraries().add(itinerary);
            itinerary.getUsers().add(user);
            _itineraryRepository.save(itinerary);
            return GenericItineraryMapper.toDto(itinerary, true);
        }
        return null;
    }
    @Override
    public ItineraryDto uploadItineraryById(UUID id) {
        Itinerary itinerary = _itineraryRepository.findById(id).orElse(null);
        if (itinerary != null && itinerary.getState().equals(ContentState.UPLOADABLE)) {
            itinerary.setState(ContentState.UPLOADED);
            _itineraryRepository.save(itinerary);
            return GenericItineraryMapper.toDto(itinerary, true);
        }
        return null;
    }
    @Override
    public AttachmentDto addAttachment(AttachmentDto attachmentDto, UserPermission permission, Predicate<Itinerary> predicate) {
        List<Itinerary> itineraries = _itineraryRepository.findAll().stream().filter(predicate).toList();
        if (!itineraries.isEmpty()) {
            Itinerary itinerary = itineraries.get(0);
            Attachment attachment = _attachmentHandler.modifyAttachment(attachmentDto, permission);
            itinerary.getAttachments().add(attachment);
            attachment.setItinerary(itinerary);
            _itineraryRepository.save(itinerary);
            return GenericAttachmentMapper.toDto(attachment, true);
        }
        return null;
    }
}
