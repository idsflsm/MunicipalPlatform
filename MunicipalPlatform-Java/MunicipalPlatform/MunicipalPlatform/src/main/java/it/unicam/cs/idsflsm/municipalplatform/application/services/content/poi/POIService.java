package it.unicam.cs.idsflsm.municipalplatform.application.services.content.poi;

import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.content.poi.IPOIService;
import it.unicam.cs.idsflsm.municipalplatform.application.criterias.content.poi.POICriteria;
import it.unicam.cs.idsflsm.municipalplatform.application.factories.attachment.AttachmentBuilderFactory;
import it.unicam.cs.idsflsm.municipalplatform.application.factories.content.poi.POIBuilderFactory;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.attachment.AuthorizedAttachmentMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.attachment.GenericAttachmentMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.attachment.PendingAttachmentMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.itinerary.GenericItineraryMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.poi.AuthorizedPOIMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.poi.GenericPOIMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.poi.PendingPOIMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.user.authenticated.GenericAuthenticatedUserMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AuthorizedAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.PendingAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.AuthorizedPOIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.POIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.PendingPOIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedUserDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.AuthorizedAttachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.PendingAttachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.AuthorizedPOI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.PendingPOI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserPermission;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.content.itinerary.IItineraryRepository;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.content.poi.IPOIRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Service
@Transactional
@AllArgsConstructor(onConstructor_ = @Autowired)
public class POIService implements IPOIService {
    private final IPOIRepository _poiRepository;
    private final IItineraryRepository _itineraryRepository;
    private final POIBuilderFactory _poiBuilderFactory;
    private final AttachmentBuilderFactory _attachmentBuilderFactory;
//    private final IAttachmentService _attachmentService;
//    private final IUserService _userService;
    @Override
    public void saveInRepository(POI poi) {
        _poiRepository.save(poi);
    }
    public List<PendingPOIDto> getAllPendingPOIs(Optional<Predicate<POI>> predicate) {
        List<POI> pois = getPOIs(predicate, PendingPOI.class);
        if (!pois.isEmpty()) {
            return GenericPOIMapper.toDto(pois, true).stream()
                    .map(poiDto -> (PendingPOIDto) poiDto)
                    .toList();
        } else {
            return null;
        }
    }
    @Override
    public List<AuthorizedPOIDto> getAllAuthorizedPOIs(Optional<Predicate<POI>> predicate) {
        List<POI> pois = getPOIs(predicate, AuthorizedPOI.class);
        if (!pois.isEmpty()) {
            return GenericPOIMapper.toDto(pois, true).stream()
                    .map(poiDto -> (AuthorizedPOIDto) poiDto)
                    .toList();
        } else {
            return null;
        }
    }
    private List<POI> getPOIs(Optional<Predicate<POI>> predicate, Class<?> type) {
        Stream<POI> pois = _poiRepository.findAll().stream();
        return predicate.map(poiPredicate -> pois.filter(poiPredicate.and((type::isInstance)))
                        .collect(Collectors.toList()))
                .orElseGet(() -> pois.filter(type::isInstance)
                        .collect(Collectors.toList()));
    }
    @Override
    public List<POI> getAllPOIs(Optional<Predicate<POI>> predicate) {
        List<POI> pois = predicate.map(poiPredicate -> _poiRepository.findAll().stream().filter(poiPredicate).toList()).orElseGet(_poiRepository::findAll);
        if (!pois.isEmpty()) {
            return pois;
        } else {
            return null;
        }
    }
    @Override
    public PendingPOIDto getPendingPOIById(UUID id) {
        return PendingPOIMapper.toDto(getPOIById(id, PendingPOI.class), true);
    }
    @Override
    public AuthorizedPOIDto getAuthorizedPOIById(UUID id) {
        return AuthorizedPOIMapper.toDto(getPOIById(id, AuthorizedPOI.class), true);
    }
    @Override
    public POI getPOIById(UUID id) {
        return _poiRepository.findById(id).orElse(null);
    }
    private <T extends POI> T getPOIById(UUID id, Class<T> type) {
        POI poi = _poiRepository.findById(id).orElse(null);
        if (poi != null) {
            return (poi.getClass() == type) ? type.cast(poi) : null;
        } else {
            return null;
        }
    }
    @Override
    public PendingPOIDto addPendingPOI(PendingPOIDto poiDto) {
        if (getPOIById(poiDto.getId(), PendingPOI.class) == null) {
            var poi = modifyPOI(poiDto, UserPermission.CONTRIBUTOR_CONTENT_CREATE_PENDING);
            _poiRepository.save(poi);
            return poiDto;
        } else {
            return null;
        }
    }
    @Override
    public AuthorizedPOIDto addAuthorizedPOI(AuthorizedPOIDto poiDto) {
        if (getPOIById(poiDto.getId(), AuthorizedPOI.class) == null) {
            var poi = modifyPOI(poiDto, UserPermission.AUTHCONTRIBUTOR_CONTENT_CREATE_AUTHORIZED);
            _poiRepository.save(poi);
            return poiDto;
        } else {
            return null;
        }
    }
    private POI modifyPOI(POIDto poiDto, UserPermission permission) {
        var builder = _poiBuilderFactory.createPOIBuilder(permission);
        builder.setName(poiDto.getName());
        builder.setCoordinates(poiDto.getCoordinates());
        builder.setDescription(poiDto.getDescription());
        builder.setAuthor(poiDto.getAuthor());
        builder.setCreationDate(poiDto.getCreationDate());
        builder.setExpiryDate(poiDto.getExpiryDate());
        builder.setContentState(poiDto.getState());
        builder.setPoiItineraries(GenericItineraryMapper.toEntity(poiDto.getPoiItineraries(), true));
        var poi = builder.build();
        poi.setId(poiDto.getId());
        return poi;
    }
    @Override
    public boolean deletePendingPOIById(UUID id) {
        PendingPOI poi = getPOIById(id, PendingPOI.class);
        if (poi != null) {
            poi.setState(ContentState.REMOVABLE);
            // _poiRepository.deleteById(id);
            _poiRepository.save(poi);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean deleteAuthorizedPOIById(UUID id) {
        AuthorizedPOI poi = getPOIById(id, AuthorizedPOI.class);
        if (poi != null) {
            List<Itinerary> poiItineraries = poi.getPoiItineraries();
//            poi.setState(ContentState.REMOVABLE);
//            // _poiRepository.deleteById(id);
//            _poiRepository.save(poi);
            poi.detachFromEntities();
            _poiRepository.delete(poi);
            poiItineraries.forEach(itinerary -> {
                if (itinerary.getItineraryPois().size() < 2) {
                    itinerary.detachFromEntities();
                    _itineraryRepository.delete(itinerary);
                }
            });
            return true;
        } else {
            return false;
        }
    }
//    @Override
//    public boolean deletePendingPOI(PendingPOIDto poiDto, Optional<Predicate<POI>> predicate) {
//        if (getPOIs(predicate, PendingPOI.class).get(0) != null) {
//            PendingPOI poi = PendingPOIMapper.toEntity(poiDto);
//            assert poi != null;
//            poi.setState(ContentState.Removable);
//            // _poiRepository.delete(poi);
//            _poiRepository.save(poi);
//            return true;
//        } else {
//            return false;
//        }
//    }
//    @Override
//    public boolean deleteAuthorizedPOI(AuthorizedPOIDto poiDto, Optional<Predicate<POI>> predicate) {
//        if (getPOIs(predicate, AuthorizedPOI.class).get(0) != null) {
//            AuthorizedPOI poi = AuthorizedPOIMapper.toEntity(poiDto);
//            assert poi != null;
//            poi.setState(ContentState.Removable);
//            // _poiRepository.delete(poi);
//            _poiRepository.save(poi);
//            return true;
//        } else {
//            return false;
//        }
//    }
    @Override
    public PendingPOIDto updatePendingPOI(PendingPOIDto poiDto) {
        var poi = modifyPOI(poiDto, UserPermission.CONTRIBUTOR_CONTENT_CREATE_PENDING);
        poi.getPoiItineraries().forEach(itinerary -> {
            itinerary.getItineraryPois().add(poi);
        });
        _poiRepository.save(poi);
        return poiDto;
    }
    @Override
    public AuthorizedPOIDto updateAuthorizedPOI(AuthorizedPOIDto poiDto) {
        var poi = modifyPOI(poiDto, UserPermission.AUTHCONTRIBUTOR_CONTENT_CREATE_AUTHORIZED);
        poi.getPoiItineraries().forEach(itinerary -> {
            itinerary.getItineraryPois().add(poi);
        });
        _poiRepository.save(poi);
        return poiDto;
    }
    @Override
    public PendingPOIDto validatePendingPOI(Optional<Predicate<POI>> predicate, boolean validate) {
        PendingPOI poi = (PendingPOI) getPOIs(predicate, PendingPOI.class).get(0);
        if (poi != null) {
            ContentState newState = (validate) ? ContentState.UPLOADABLE : ContentState.REMOVABLE;
            poi.setState(newState);
            _poiRepository.save(poi);
            return PendingPOIMapper.toDto(poi, true);
        } else {
            return null;
        }
    }
//    @Override
//    public boolean savePendingPOI(PendingPOIDto poiDto, UUID idAuthenticatedTourist) {
//        AuthenticatedTourist authenticatedTourist = (AuthenticatedTourist)(_authenticatedUserRepository.findById(idAuthenticatedTourist).orElse(null));
//        if (authenticatedTourist != null) {
//            PendingPOI poi = PendingPOIMapper.toEntity(poiDto);
//            assert poi != null;
//            // AuthenticatedTourist tourist = AuthenticatedTouristMapper.toEntity(touristDto);
//            authenticatedTourist.getPois().add(poi);
//            _authenticatedUserRepository.save(authenticatedTourist);
//            return true;
//        } else {
//            return false;
//        }
//    }
//    @Override
//    public boolean saveAuthorizedPOI(AuthorizedPOIDto poiDto, UUID idAuthenticatedTourist) {
//        AuthenticatedTourist authenticatedTourist = (AuthenticatedTourist)(_authenticatedUserRepository.findById(idAuthenticatedTourist).orElse(null));
//        if (authenticatedTourist != null) {
//            AuthorizedPOI poi = AuthorizedPOIMapper.toEntity(poiDto);
//            assert poi != null;
//            // AuthenticatedTourist tourist = AuthenticatedTouristMapper.toEntity(touristDto);
//            authenticatedTourist.getPois().add(poi);
//            _authenticatedUserRepository.save(authenticatedTourist);
//            return true;
//        } else {
//            return false;
//        }
//    }
    @Override
    public PendingPOIDto savePendingPOI(UUID id, AuthenticatedUserDto userDto) {
        AuthenticatedUser authenticatedUser = GenericAuthenticatedUserMapper.toEntity(userDto, true);
        PendingPOI poi = getPOIById(id, PendingPOI.class);
        if (authenticatedUser != null && poi != null && poi.getState().equals(ContentState.UPLOADED)) {
            authenticatedUser.getPois().add(poi);
            poi.getUsers().add(authenticatedUser);
//            _userService.saveInRepository(authenticatedUser);
            _poiRepository.save(poi);
            return PendingPOIMapper.toDto(poi, true);
        } else {
            return null;
        }
    }
    @Override
    public AuthorizedPOIDto saveAuthorizedPOI(UUID id, AuthenticatedUserDto userDto) {
        AuthenticatedUser authenticatedUser = GenericAuthenticatedUserMapper.toEntity(userDto, true);
        AuthorizedPOI poi = getPOIById(id, AuthorizedPOI.class);
        if (authenticatedUser != null && poi != null && poi.getState().equals(ContentState.UPLOADED)) {
            authenticatedUser.getPois().add(poi);
            poi.getUsers().add(authenticatedUser);
//            _userService.saveInRepository(authenticatedUser);
            _poiRepository.save(poi);
            return AuthorizedPOIMapper.toDto(poi, true);
        } else {
            return null;
        }
    }
    @Override
    public List<PendingPOIDto> uploadAllPendingPOIs() {
        List<POI> pois = getPOIs(Optional.of(POICriteria.criteriaBuilder(POICriteria.isPendingPoi(), POICriteria.isInUploadableState())), PendingPOI.class);
        pois = pois.stream().peek(poi -> {
            poi.setState(ContentState.UPLOADED);
            _poiRepository.save(poi);
        }).toList();
        return GenericPOIMapper.toDto(pois, true).stream().map(poiDto -> (PendingPOIDto) poiDto).toList();
    }
    @Override
    public PendingPOIDto uploadPendingPOI(UUID id) {
        PendingPOI poi = getPOIById(id, PendingPOI.class);
        if (poi != null && poi.getState().equals(ContentState.UPLOADABLE)) {
            poi.setState(ContentState.UPLOADED);
            _poiRepository.save(poi);
            return PendingPOIMapper.toDto(poi, true);
        } else {
            return null;
        }
    }
    @Override
    public List<AuthorizedPOIDto> uploadAllAuthorizedPOIs() {
        List<POI> pois = getPOIs(Optional.of(POICriteria.criteriaBuilder(POICriteria.isAuthorizedPoi(), POICriteria.isInUploadableState())), AuthorizedPOI.class);
//        pois = pois.stream().peek(poi -> {
//            poi.setState(ContentState.UPLOADED);
//            _poiRepository.save(poi);
//        }).toList();
        pois.forEach(poi -> {
            poi.setState(ContentState.UPLOADED);
            _poiRepository.save(poi);
        });
        return GenericPOIMapper.toDto(pois, true).stream().map(poiDto -> (AuthorizedPOIDto) poiDto).toList();
    }
    @Override
    public AuthorizedPOIDto uploadAuthorizedPOI(UUID id) {
        AuthorizedPOI poi = getPOIById(id, AuthorizedPOI.class);
        if (poi != null && poi.getState().equals(ContentState.UPLOADABLE)) {
            poi.setState(ContentState.UPLOADED);
            _poiRepository.save(poi);
            return AuthorizedPOIMapper.toDto(poi, true);
        } else {
            return null;
        }
    }
    @Override
    public AttachmentDto addPendingAttachment(PendingAttachmentDto attachmentDto, Optional<Predicate<POI>> predicate) {
        POI poi = getPOIs(predicate, POI.class).get(0);
        if (poi != null) {
            var attachment = addAttachment(attachmentDto, UserPermission.AUTHTOURIST_ATTACHMENT_CREATE);
            poi.getAttachments().add(attachment);
            attachment.setPoi(poi);
            _poiRepository.save(poi);
//            _attachmentService.saveInRepository(attachment);
            return GenericAttachmentMapper.toDto(attachment, true);
        } else {
            return null;
        }
    }
    @Override
    public AttachmentDto addAuthorizedAttachment(AuthorizedAttachmentDto attachmentDto, Optional<Predicate<POI>> predicate) {
        POI poi = getPOIs(predicate, POI.class).get(0);
        if (poi != null) {
            var attachment = addAttachment(attachmentDto, UserPermission.AUTHCONTRIBUTOR_ATTACHMENT_CREATE_AUTHORIZED);
            poi.getAttachments().add(attachment);
            attachment.setPoi(poi);
            _poiRepository.save(poi);
//                _attachmentService.saveInRepository(attachment);
            return GenericAttachmentMapper.toDto(attachment, true);
        } else {
            return null;
        }
    }
    private Attachment addAttachment(AttachmentDto attachmentDto, UserPermission permission) {
        var builder = _attachmentBuilderFactory.createAttachmentBuilder(permission);
        builder.setName(attachmentDto.getName());
        builder.setDescription(attachmentDto.getDescription());
        builder.setAuthor(attachmentDto.getAuthor());
        builder.setCreationDate(attachmentDto.getCreationDate());
        builder.setExpiryDate(attachmentDto.getExpiryDate());
        builder.setState(attachmentDto.getState());
        var attachment = builder.build();
        attachment.setId(attachmentDto.getId());
        return attachment;
    }
}

