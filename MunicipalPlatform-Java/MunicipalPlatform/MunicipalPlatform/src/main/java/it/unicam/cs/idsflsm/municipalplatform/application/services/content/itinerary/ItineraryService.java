package it.unicam.cs.idsflsm.municipalplatform.application.services.content.itinerary;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.attachment.IAttachmentService;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.content.itinerary.IItineraryService;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.attachment.AuthorizedAttachmentMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.attachment.PendingAttachmentMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.itinerary.AuthorizedItineraryMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.itinerary.PendingItineraryMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AuthorizedAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.PendingAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.AuthorizedItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.PendingItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.AuthorizedAttachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.PendingAttachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.AuthorizedItinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.PendingItinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedTourist;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.content.itinerary.IItineraryRepository;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.user.IAuthenticatedUserRepository;
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
    private final IAttachmentService _attachmentService;
    @Override
    public void saveInRepository(Itinerary itinerary) {
        _itineraryRepository.save(itinerary);
    }
    //TODO : CHANGE WITH AUTHENTICATEDUSERSERVICE
    private final IAuthenticatedUserRepository _authenticatedUserRepository;
    @Override
    public List<PendingItineraryDto> getAllPendingItineraries(Optional<Predicate<Itinerary>> predicate) {
        List<PendingItinerary> itineraries = getItineraries(predicate, PendingItinerary.class);
        if (!itineraries.isEmpty()) {
            return PendingItineraryMapper.toDto(itineraries);
        } else {
            return null;
        }
    }
    @Override
    public List<AuthorizedItineraryDto> getAllAuthorizedItineraries(Optional<Predicate<Itinerary>> predicate) {
        List<AuthorizedItinerary> itineraries = getItineraries(predicate, AuthorizedItinerary.class);
        if (!itineraries.isEmpty()) {
            return AuthorizedItineraryMapper.toDto(itineraries);
        } else {
            return null;
        }
    }
    private <T extends Itinerary> List<T> getItineraries(Optional<Predicate<Itinerary>> predicate, Class<T> type) {
        Stream<Itinerary> itineraries = _itineraryRepository.findAll().stream();
        return predicate.map(itineraryPredicate -> itineraries.filter(itineraryPredicate.and((type::isInstance)))
                        .map(type::cast)
                        .collect(Collectors.toList()))
                .orElseGet(() -> itineraries.filter(type::isInstance)
                        .map(type::cast)
                        .collect(Collectors.toList()));
    }
    @Override
    public PendingItineraryDto getPendingItineraryById(UUID id) {
        return PendingItineraryMapper.toDto(getItineraryById(id, PendingItinerary.class));
    }
    @Override
    public AuthorizedItineraryDto getAuthorizedItineraryById(UUID id) {
        return AuthorizedItineraryMapper.toDto(getItineraryById(id, AuthorizedItinerary.class));
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
    public boolean addPendingItinerary(PendingItineraryDto itineraryDto) {
        if (getItineraryById(itineraryDto.getId(), PendingItinerary.class) == null) {
            PendingItinerary itinerary = PendingItineraryMapper.toEntity(itineraryDto);
            itinerary.setState(ContentState.VALIDABLE);
            _itineraryRepository.save(itinerary);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean addAuthorizedItinerary(AuthorizedItineraryDto itineraryDto) {
        if (getItineraryById(itineraryDto.getId(), AuthorizedItinerary.class) == null) {
            AuthorizedItinerary itinerary = AuthorizedItineraryMapper.toEntity(itineraryDto);
            itinerary.setState(ContentState.UPLOADABLE);
            _itineraryRepository.save(itinerary);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean deletePendingItineraryById(UUID id) {
        PendingItinerary itinerary = getItineraryById(id, PendingItinerary.class);
        if (itinerary != null) {
            itinerary.setState(ContentState.REMOVABLE);
            // _itineraryRepository.deleteById(id);
            _itineraryRepository.save(itinerary);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean deleteAuthorizedItineraryById(UUID id) {
        AuthorizedItinerary itinerary = getItineraryById(id, AuthorizedItinerary.class);
        if (itinerary != null) {
            itinerary.setState(ContentState.REMOVABLE);
            // _itineraryRepository.deleteById(id);
            _itineraryRepository.save(itinerary);
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
    public boolean updatePendingItinerary(PendingItineraryDto itineraryDto, Optional<Predicate<Itinerary>> predicate) {
        if (getItineraries(predicate, PendingItinerary.class).get(0) != null) {
            PendingItinerary itinerary = PendingItineraryMapper.toEntity(itineraryDto);
            assert itinerary != null;
            _itineraryRepository.save(itinerary);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean updateAuthorizedItinerary(AuthorizedItineraryDto itineraryDto, Optional<Predicate<Itinerary>> predicate) {
        if (getItineraries(predicate, AuthorizedItinerary.class).get(0) != null) {
            AuthorizedItinerary itinerary = AuthorizedItineraryMapper.toEntity(itineraryDto);
            assert itinerary != null;
            _itineraryRepository.save(itinerary);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean validatePendingItinerary(PendingItineraryDto itineraryDto, Optional<Predicate<Itinerary>> predicate, boolean validate) {
        PendingItinerary itinerary = PendingItineraryMapper.toEntity(itineraryDto);
        assert itinerary != null;
        if (getItineraries(predicate, PendingItinerary.class).get(0) != null) {
            ContentState newState = (validate) ? ContentState.UPLOADABLE : ContentState.REMOVABLE;
            itinerary.setState(newState);
            _itineraryRepository.save(itinerary);
            return true;
        } else {
            return false;
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
    public boolean savePendingItinerary(UUID id, UUID idAuthenticatedTourist) {
        AuthenticatedTourist authenticatedTourist = (AuthenticatedTourist)(_authenticatedUserRepository.findById(idAuthenticatedTourist).orElse(null));
        PendingItineraryDto itineraryDto = getPendingItineraryById(id);
        if (authenticatedTourist != null && itineraryDto != null && itineraryDto.getState().equals(ContentState.UPLOADED)) {
            PendingItinerary itinerary = PendingItineraryMapper.toEntity(itineraryDto);
            authenticatedTourist.getItineraries().add(itinerary);
            itinerary.getTourists().add(authenticatedTourist);
            _authenticatedUserRepository.save(authenticatedTourist);
            _itineraryRepository.save(itinerary);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean saveAuthorizedItinerary(UUID id, UUID idAuthenticatedTourist) {
        AuthenticatedTourist authenticatedTourist = (AuthenticatedTourist)(_authenticatedUserRepository.findById(idAuthenticatedTourist).orElse(null));
        AuthorizedItineraryDto itineraryDto = getAuthorizedItineraryById(id);
        if (authenticatedTourist != null && itineraryDto != null && itineraryDto.getState().equals(ContentState.UPLOADED)) {
            AuthorizedItinerary itinerary = AuthorizedItineraryMapper.toEntity(itineraryDto);
            authenticatedTourist.getItineraries().add(itinerary);
            itinerary.getTourists().add(authenticatedTourist);
            _authenticatedUserRepository.save(authenticatedTourist);
            _itineraryRepository.save(itinerary);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean addPendingAttachment(PendingAttachmentDto attachmentDto, Optional<Predicate<Itinerary>> predicate) {
        Itinerary itinerary = getItineraries(predicate, Itinerary.class).get(0);
        if (itinerary != null) {
            boolean result = _attachmentService.addPendingAttachment(attachmentDto);
            if (result) {
                PendingAttachment attachment = PendingAttachmentMapper.toEntity(attachmentDto);
                itinerary.getAttachments().add(attachment);
                attachment.setItinerary(itinerary);
                _itineraryRepository.save(itinerary);
                _attachmentService.saveInRepository(attachment);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    @Override
    public boolean addAuthorizedAttachment(AuthorizedAttachmentDto attachmentDto, Optional<Predicate<Itinerary>> predicate) {
        Itinerary itinerary = getItineraries(predicate, Itinerary.class).get(0);
        if (itinerary != null) {
            boolean result = _attachmentService.addAuthorizedAttachment(attachmentDto);
            if (result) {
                AuthorizedAttachment attachment = AuthorizedAttachmentMapper.toEntity(attachmentDto);
                itinerary.getAttachments().add(attachment);
                attachment.setItinerary(itinerary);
                _itineraryRepository.save(itinerary);
                _attachmentService.saveInRepository(attachment);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
