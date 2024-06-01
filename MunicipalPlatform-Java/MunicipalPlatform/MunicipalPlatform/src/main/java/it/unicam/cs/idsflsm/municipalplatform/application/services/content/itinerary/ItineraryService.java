package it.unicam.cs.idsflsm.municipalplatform.application.services.content.itinerary;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.content.itinerary.IItineraryService;
import it.unicam.cs.idsflsm.municipalplatform.application.criterias.content.itinerary.ItineraryCriteria;
import it.unicam.cs.idsflsm.municipalplatform.application.factories.attachment.AttachmentBuilderFactory;
import it.unicam.cs.idsflsm.municipalplatform.application.factories.content.itinerary.ItineraryBuilderFactory;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.attachment.AuthorizedAttachmentMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.attachment.GenericAttachmentMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.attachment.PendingAttachmentMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.itinerary.AuthorizedItineraryMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.itinerary.GenericItineraryMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.itinerary.PendingItineraryMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.poi.GenericPOIMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.user.authenticated.GenericAuthenticatedUserMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AuthorizedAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.PendingAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.AuthorizedItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.ItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.PendingItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedUserDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.AuthorizedAttachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.PendingAttachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.AuthorizedItinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.PendingItinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserPermission;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.content.itinerary.IItineraryRepository;
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
public class ItineraryService implements IItineraryService {
    private final IItineraryRepository _itineraryRepository;
    private final ItineraryBuilderFactory _itineraryBuilderFactory;
    private final AttachmentBuilderFactory _attachmentBuilderFactory;
//    private final IPOIService _poiService;
//    private final IAttachmentService _attachmentService;
//    private final IUserService _userService;
    @Override
    public void saveInRepository(Itinerary itinerary) {
        _itineraryRepository.save(itinerary);
    }
    public List<PendingItineraryDto> getAllPendingItineraries(Optional<Predicate<Itinerary>> predicate) {
        List<Itinerary> itineraries = getItineraries(predicate, PendingItinerary.class);
        if (!itineraries.isEmpty()) {
            return GenericItineraryMapper.toDto(itineraries, true).stream()
                    .map(itineraryDto -> (PendingItineraryDto) itineraryDto)
                    .toList();
        } else {
            return null;
        }
    }
    @Override
    public List<AuthorizedItineraryDto> getAllAuthorizedItineraries(Optional<Predicate<Itinerary>> predicate) {
        List<Itinerary> itineraries = getItineraries(predicate, AuthorizedItinerary.class);
        if (!itineraries.isEmpty()) {
            return GenericItineraryMapper.toDto(itineraries, true).stream()
                    .map(itineraryDto -> (AuthorizedItineraryDto) itineraryDto)
                    .toList();
        } else {
            return null;
        }
    }
    private List<Itinerary> getItineraries(Optional<Predicate<Itinerary>> predicate, Class<?> type) {
        Stream<Itinerary> itineraries = _itineraryRepository.findAll().stream();
        return predicate.map(itineraryPredicate -> itineraries
                        .filter(itineraryPredicate.and((type::isInstance)))
                        .collect(Collectors.toList()))
                .orElseGet(() -> itineraries
                        .filter(type::isInstance)
                        .collect(Collectors.toList()));
    }
    @Override
    public PendingItineraryDto getPendingItineraryById(UUID id) {
        return PendingItineraryMapper.toDto(getItineraryById(id, PendingItinerary.class), true);
    }
    @Override
    public AuthorizedItineraryDto getAuthorizedItineraryById(UUID id) {
        return AuthorizedItineraryMapper.toDto(getItineraryById(id, AuthorizedItinerary.class), true);
    }
    private <T extends Itinerary> T getItineraryById(UUID id, Class<T> type) {
        Itinerary itinerary = _itineraryRepository.findById(id).orElse(null);
        if (itinerary != null) {
            return (itinerary.getClass() == type) ? type.cast(itinerary) : null;
        } else {
            return null;
        }
    }
    @Override
    public PendingItineraryDto addPendingItinerary(PendingItineraryDto itineraryDto) {
        if (getItineraryById(itineraryDto.getId(), PendingItinerary.class) == null) {
            var itinerary = modifyItinerary(itineraryDto, UserPermission.CONTRIBUTOR_CONTENT_CREATE_PENDING);
            itinerary.getItineraryPois().forEach(poi -> {
                poi.getPoiItineraries().add(itinerary);
                // _poiService.saveInRepository(poi);
            });
            _itineraryRepository.save(itinerary);
            return itineraryDto;
        } else {
            return null;
        }
    }
    @Override
    public AuthorizedItineraryDto addAuthorizedItinerary(AuthorizedItineraryDto itineraryDto) {
        if (getItineraryById(itineraryDto.getId(), AuthorizedItinerary.class) == null) {
            var itinerary = modifyItinerary(itineraryDto, UserPermission.AUTHCONTRIBUTOR_CONTENT_CREATE_AUTHORIZED);
            itinerary.getItineraryPois().forEach(poi -> {
                poi.getPoiItineraries().add(itinerary);
                // _poiService.saveInRepository(poi);
            });
            _itineraryRepository.save(itinerary);
            return itineraryDto;
        } else {
            return null;
        }
    }
    private Itinerary modifyItinerary(ItineraryDto itineraryDto, UserPermission permission) {
        var builder = _itineraryBuilderFactory.createItineraryBuilder(permission);
        builder.setName(itineraryDto.getName());
        builder.setCoordinates(itineraryDto.getCoordinates());
        builder.setDescription(itineraryDto.getDescription());
        builder.setAuthor(itineraryDto.getAuthor());
        builder.setCreationDate(itineraryDto.getCreationDate());
        builder.setExpiryDate(itineraryDto.getExpiryDate());
        builder.setContentState(itineraryDto.getState());
        builder.setItineraryPois(GenericPOIMapper.toEntity(itineraryDto.getItineraryPois(), true));
        var itinerary = builder.build();
        itinerary.setId(itineraryDto.getId());
        return itinerary;
    }
//    @Override
//    public boolean deletePendingItineraryById(UUID id) {
//        PendingItinerary itinerary = getItineraryById(id, PendingItinerary.class);
//        if (itinerary != null) {
//            itinerary.setState(ContentState.REMOVABLE);
//            // _itineraryRepository.deleteById(id);
//            _itineraryRepository.save(itinerary);
//            return true;
//        } else {
//            return false;
//        }
//    }
    @Override
    public boolean deleteAuthorizedItineraryById(UUID id) {
        AuthorizedItinerary itinerary = getItineraryById(id, AuthorizedItinerary.class);
        if (itinerary != null) {
////            itinerary.setState(ContentState.REMOVABLE);
////            // _itineraryRepository.deleteById(id);
////            _itineraryRepository.save(itinerary);
//            itinerary.getItineraryPois().forEach(poi -> poi.getPoiItineraries().remove(itinerary));
//            itinerary.setItineraryPois(null);
//            itinerary.getUsers().forEach(authenticatedUser -> authenticatedUser.getItineraries().remove(itinerary));
//            itinerary.setUsers(null);
////            itinerary.getAttachments().forEach(attachment -> attachment.setItinerary(null));
////            itinerary.setAttachments(null);
            itinerary.detachFromEntities();
            _itineraryRepository.delete(itinerary);
            return true;
        } else {
            return false;
        }
    }
//    @Override
//    public boolean deletePendingItinerary(PendingItineraryDto itineraryDto, Optional<Predicate<Itinerary>> predicate) {
//        if (getItineraries(predicate, PendingItinerary.class).get(0) != null) {
//            PendingItinerary itinerary = PendingItineraryMapper.toEntity(itineraryDto);
//            assert itinerary != null;
//            itinerary.setState(ContentState.Removable);
//            // _itineraryRepository.delete(itinerary);
//            _itineraryRepository.save(itinerary);
//            return true;
//        } else {
//            return false;
//        }
//    }
//    @Override
//    public boolean deleteAuthorizedItinerary(AuthorizedItineraryDto itineraryDto, Optional<Predicate<Itinerary>> predicate) {
//        if (getItineraries(predicate, PendingItinerary.class).get(0) != null) {
//            AuthorizedItinerary itinerary = AuthorizedItineraryMapper.toEntity(itineraryDto);
//            assert itinerary != null;
//            itinerary.setState(ContentState.Removable);
//            // _itineraryRepository.delete(itinerary);
//            _itineraryRepository.save(itinerary);
//            return true;
//        } else {
//            return false;
//        }
//    }
    @Override
    public PendingItineraryDto updatePendingItinerary(PendingItineraryDto itineraryDto) {
        var itinerary = modifyItinerary(itineraryDto, UserPermission.CONTRIBUTOR_CONTENT_CREATE_PENDING);
        itinerary.getItineraryPois().forEach(poi -> {
            poi.getPoiItineraries().add(itinerary);
//            _poiService.saveInRepository(poi);
        });
        _itineraryRepository.save(itinerary);
        return itineraryDto;
    }
    @Override
    public AuthorizedItineraryDto updateAuthorizedItinerary(AuthorizedItineraryDto itineraryDto) {
        var itinerary = modifyItinerary(itineraryDto, UserPermission.AUTHCONTRIBUTOR_CONTENT_CREATE_AUTHORIZED);
        itinerary.getItineraryPois().forEach(poi -> {
            poi.getPoiItineraries().add(itinerary);
//            _poiService.saveInRepository(poi);
        });
        _itineraryRepository.save(itinerary);
        return itineraryDto;
    }
    @Override
    public PendingItineraryDto validatePendingItinerary(Optional<Predicate<Itinerary>> predicate, boolean validate) {
        PendingItinerary itinerary = (PendingItinerary) getItineraries(predicate, PendingItinerary.class).get(0);
        if (itinerary != null) {
            ContentState newState = (validate) ? ContentState.UPLOADABLE : ContentState.REMOVABLE;
            itinerary.setState(newState);
            _itineraryRepository.save(itinerary);
            return PendingItineraryMapper.toDto(itinerary, true);
        } else {
            return null;
        }
    }
//    @Override
//    public boolean savePendingItinerary(PendingItineraryDto itineraryDto, UUID idAuthenticatedTourist) {
//        AuthenticatedTourist authenticatedTourist = (AuthenticatedTourist)(_authenticatedUserRepository.findById(idAuthenticatedTourist).orElse(null));
//        if (authenticatedTourist != null) {
//            PendingItinerary itinerary = PendingItineraryMapper.toEntity(itineraryDto);
//            assert itinerary != null;
//            // AuthenticatedTourist tourist = AuthenticatedTouristMapper.toEntity(touristDto);
//            authenticatedTourist.getItineraries().add(itinerary);
//            _authenticatedUserRepository.save(authenticatedTourist);
//            return true;
//        } else {
//            return false;
//        }
//    }
//    @Override
//    public boolean saveAuthorizedItinerary(AuthorizedItineraryDto itineraryDto, UUID idAuthenticatedTourist) {
//        AuthenticatedTourist authenticatedTourist = (AuthenticatedTourist)(_authenticatedUserRepository.findById(idAuthenticatedTourist).orElse(null));
//        if (authenticatedTourist != null) {
//            AuthorizedItinerary itinerary = AuthorizedItineraryMapper.toEntity(itineraryDto);
//            assert itinerary != null;
//            // AuthenticatedTourist tourist = AuthenticatedTouristMapper.toEntity(touristDto);
//            authenticatedTourist.getItineraries().add(itinerary);
//            _authenticatedUserRepository.save(authenticatedTourist);
//            return true;
//        } else {
//            return false;
//        }
//    }
    @Override
    public PendingItineraryDto savePendingItinerary(UUID id, AuthenticatedUserDto userDto) {
        AuthenticatedUser authenticatedUser = GenericAuthenticatedUserMapper.toEntity(userDto, true);
        PendingItinerary itinerary = getItineraryById(id, PendingItinerary.class);
        if (authenticatedUser != null && itinerary != null && itinerary.getState().equals(ContentState.UPLOADED)) {
            authenticatedUser.getItineraries().add(itinerary);
            itinerary.getUsers().add(authenticatedUser);
//            _userService.saveInRepository(authenticatedUser);
            _itineraryRepository.save(itinerary);
            return PendingItineraryMapper.toDto(itinerary, true);
        } else {
            return null;
        }
    }
    @Override
    public AuthorizedItineraryDto saveAuthorizedItinerary(UUID id, AuthenticatedUserDto userDto) {
        AuthenticatedUser authenticatedUser = GenericAuthenticatedUserMapper.toEntity(userDto, true);
        AuthorizedItinerary itinerary = getItineraryById(id, AuthorizedItinerary.class);
        if (authenticatedUser != null && itinerary != null && itinerary.getState().equals(ContentState.UPLOADED)) {
            authenticatedUser.getItineraries().add(itinerary);
            itinerary.getUsers().add(authenticatedUser);
//            _userService.saveInRepository(authenticatedUser);
            _itineraryRepository.save(itinerary);
            return AuthorizedItineraryMapper.toDto(itinerary, true);
        } else {
            return null;
        }
    }
    @Override
    public List<PendingItineraryDto> uploadAllPendingItineraries() {
        List<Itinerary> itineraries = getItineraries(Optional.of(ItineraryCriteria.criteriaBuilder(ItineraryCriteria.isPendingItinerary(), ItineraryCriteria.isInUploadableState())), PendingItinerary.class);
        itineraries = itineraries.stream().peek(itinerary -> {
            itinerary.setState(ContentState.UPLOADED);
            _itineraryRepository.save(itinerary);
        }).toList();
        return GenericItineraryMapper.toDto(itineraries, true).stream().map(itineraryDto -> (PendingItineraryDto) itineraryDto).toList();
    }
    @Override
    public PendingItineraryDto uploadPendingItinerary(UUID id) {
        PendingItinerary itinerary = getItineraryById(id, PendingItinerary.class);
        if (itinerary != null && itinerary.getState().equals(ContentState.UPLOADABLE)) {
            itinerary.setState(ContentState.UPLOADED);
            _itineraryRepository.save(itinerary);
            return PendingItineraryMapper.toDto(itinerary, true);
        } else {
            return null;
        }
    }
    @Override
    public List<AuthorizedItineraryDto> uploadAllAuthorizedItineraries() {
        List<Itinerary> itineraries = getItineraries(Optional.of(ItineraryCriteria.criteriaBuilder(ItineraryCriteria.isAuthorizedItinerary(), ItineraryCriteria.isInUploadableState())), AuthorizedItinerary.class);
        itineraries = itineraries.stream().peek(itinerary -> {
            itinerary.setState(ContentState.UPLOADED);
            _itineraryRepository.save(itinerary);
        }).toList();
        return GenericItineraryMapper.toDto(itineraries, true).stream().map(itineraryDto -> (AuthorizedItineraryDto) itineraryDto).toList();
    }
    @Override
    public AuthorizedItineraryDto uploadAuthorizedItinerary(UUID id) {
        AuthorizedItinerary itinerary = getItineraryById(id, AuthorizedItinerary.class);
        if (itinerary != null && itinerary.getState().equals(ContentState.UPLOADABLE)) {
            itinerary.setState(ContentState.UPLOADED);
            _itineraryRepository.save(itinerary);
            return AuthorizedItineraryMapper.toDto(itinerary, true);
        } else {
            return null;
        }
    }
    @Override
    public AttachmentDto addPendingAttachment(PendingAttachmentDto attachmentDto, Optional<Predicate<Itinerary>> predicate) {
        Itinerary itinerary = getItineraries(predicate, Itinerary.class).get(0);
        if (itinerary != null) {
            var attachment = addAttachment(attachmentDto, UserPermission.AUTHTOURIST_ATTACHMENT_CREATE);
            itinerary.getAttachments().add(attachment);
            attachment.setItinerary(itinerary);
            _itineraryRepository.save(itinerary);
//                _attachmentService.saveInRepository(attachment);
            return GenericAttachmentMapper.toDto(attachment, true);
        } else {
            return null;
        }
    }
    @Override
    public AttachmentDto addAuthorizedAttachment(AuthorizedAttachmentDto attachmentDto, Optional<Predicate<Itinerary>> predicate) {
        Itinerary itinerary = getItineraries(predicate, Itinerary.class).get(0);
        if (itinerary != null) {
            var attachment = addAttachment(attachmentDto, UserPermission.AUTHCONTRIBUTOR_ATTACHMENT_CREATE_AUTHORIZED);
            itinerary.getAttachments().add(attachment);
            attachment.setItinerary(itinerary);
            _itineraryRepository.save(itinerary);
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
