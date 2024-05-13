package it.unicam.cs.idsflsm.municipalplatform.application.services.content.poi;

import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.attachment.IAttachmentService;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.content.poi.IPOIService;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.attachment.AuthorizedAttachmentMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.attachment.PendingAttachmentMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.poi.AuthorizedPOIMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.poi.PendingPOIMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AuthorizedAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.PendingAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.AuthorizedPOIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.PendingPOIDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.AuthorizedAttachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.PendingAttachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.AuthorizedPOI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.PendingPOI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedTourist;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.content.poi.IPOIRepository;
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
public class POIService implements IPOIService {
    private final IPOIRepository _poiRepository;
    private final IAttachmentService _attachmentService;
    @Override
    public void saveInRepository(POI poi) {
        _poiRepository.save(poi);
    }
    //TODO : CHANGE WITH AUTHENTICATEDUSERSERVICE
    private final IAuthenticatedUserRepository _authenticatedUserRepository;
    @Override
    public List<PendingPOIDto> getAllPendingPOIs(Optional<Predicate<POI>> predicate) {
        List<PendingPOI> pois = getPOIs(predicate, PendingPOI.class);
        if (!pois.isEmpty()) {
            return PendingPOIMapper.toDto(pois);
        } else {
            return null;
        }
    }
    @Override
    public List<AuthorizedPOIDto> getAllAuthorizedPOIs(Optional<Predicate<POI>> predicate) {
        List<AuthorizedPOI> pois = getPOIs(predicate, AuthorizedPOI.class);
        if (!pois.isEmpty()) {
            return AuthorizedPOIMapper.toDto(pois);
        } else {
            return null;
        }
    }
    private <T extends POI> List<T> getPOIs(Optional<Predicate<POI>> predicate, Class<T> type) {
        Stream<POI> pois = _poiRepository.findAll().stream();
        return predicate.map(poiPredicate -> pois.filter(poiPredicate.and((type::isInstance)))
                        .map(type::cast)
                        .collect(Collectors.toList()))
                .orElseGet(() -> pois.filter(type::isInstance)
                        .map(type::cast)
                        .collect(Collectors.toList()));
    }
    @Override
    public PendingPOIDto getPendingPOIById(UUID id) {
        return PendingPOIMapper.toDto(getPOIById(id, PendingPOI.class));
    }
    @Override
    public AuthorizedPOIDto getAuthorizedPOIById(UUID id) {
        return AuthorizedPOIMapper.toDto(getPOIById(id, AuthorizedPOI.class));
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
    public boolean addPendingPOI(PendingPOIDto poiDto) {
        if (getPOIById(poiDto.getId(), PendingPOI.class) == null) {
            PendingPOI poi = PendingPOIMapper.toEntity(poiDto);
            poi.setState(ContentState.VALIDABLE);
            _poiRepository.save(poi);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean addAuthorizedPOI(AuthorizedPOIDto poiDto) {
        if (getPOIById(poiDto.getId(), AuthorizedPOI.class) == null) {
            AuthorizedPOI poi = AuthorizedPOIMapper.toEntity(poiDto);
            poi.setState(ContentState.UPLOADABLE);
            _poiRepository.save(poi);
            return true;
        } else {
            return false;
        }
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
            poi.setState(ContentState.REMOVABLE);
            // _poiRepository.deleteById(id);
            _poiRepository.save(poi);
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
    public boolean updatePendingPOI(PendingPOIDto poiDto, Optional<Predicate<POI>> predicate) {
        if (getPOIs(predicate, PendingPOI.class).get(0) != null) {
            PendingPOI poi = PendingPOIMapper.toEntity(poiDto);
            assert poi != null;
            _poiRepository.save(poi);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean updateAuthorizedPOI(AuthorizedPOIDto poiDto, Optional<Predicate<POI>> predicate) {
        if (getPOIs(predicate, AuthorizedPOI.class).get(0) != null) {
            AuthorizedPOI poi = AuthorizedPOIMapper.toEntity(poiDto);
            assert poi != null;
            _poiRepository.save(poi);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean validatePendingPOI(PendingPOIDto poiDto, Optional<Predicate<POI>> predicate, boolean validate) {
        PendingPOI poi = PendingPOIMapper.toEntity(poiDto);
        assert poi != null;
        if (getPOIs(predicate, PendingPOI.class).get(0) != null) {
            ContentState newState = (validate) ? ContentState.UPLOADABLE : ContentState.REMOVABLE;
            poi.setState(newState);
            _poiRepository.save(poi);
            return true;
        } else {
            return false;
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
    public boolean savePendingPOI(UUID id, UUID idAuthenticatedTourist) {
        AuthenticatedTourist authenticatedTourist = (AuthenticatedTourist)(_authenticatedUserRepository.findById(idAuthenticatedTourist).orElse(null));
        PendingPOIDto poiDto = getPendingPOIById(id);
        if (authenticatedTourist != null && poiDto != null) {
            PendingPOI poi = PendingPOIMapper.toEntity(poiDto);
            authenticatedTourist.getPois().add(poi);
            poi.getTourists().add(authenticatedTourist);
            _authenticatedUserRepository.save(authenticatedTourist);
            _poiRepository.save(poi);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean saveAuthorizedPOI(UUID id, UUID idAuthenticatedTourist) {
        AuthenticatedTourist authenticatedTourist = (AuthenticatedTourist)(_authenticatedUserRepository.findById(idAuthenticatedTourist).orElse(null));
        AuthorizedPOIDto poiDto = getAuthorizedPOIById(id);
        if (authenticatedTourist != null && poiDto != null) {
            AuthorizedPOI poi = AuthorizedPOIMapper.toEntity(poiDto);
            authenticatedTourist.getPois().add(poi);
            poi.getTourists().add(authenticatedTourist);
            _authenticatedUserRepository.save(authenticatedTourist);
            _poiRepository.save(poi);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean addPendingAttachment(PendingAttachmentDto attachmentDto, Optional<Predicate<POI>> predicate) {
        POI poi = getPOIs(predicate, POI.class).get(0);
        // TODO : DUPLICATED CODE
        if (poi != null) {
            boolean result = _attachmentService.addPendingAttachment(attachmentDto);
            if (result) {
                PendingAttachment attachment = PendingAttachmentMapper.toEntity(attachmentDto);
                poi.getAttachments().add(attachment);
                attachment.setPoi(poi);
                _poiRepository.save(poi);
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
    public boolean addAuthorizedAttachment(AuthorizedAttachmentDto attachmentDto, Optional<Predicate<POI>> predicate) {
        POI poi = getPOIs(predicate, POI.class).get(0);
        // TODO : DUPLICATED CODE
        if (poi != null) {
            boolean result = _attachmentService.addAuthorizedAttachment(attachmentDto);
            if (result) {
                AuthorizedAttachment attachment = AuthorizedAttachmentMapper.toEntity(attachmentDto);
                poi.getAttachments().add(attachment);
                attachment.setPoi(poi);
                _poiRepository.save(poi);
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

