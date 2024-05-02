package it.unicam.cs.idsflsm.municipalplatform.application.services.poi;

import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.poi.IPOIService;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.attachment.AuthorizedAttachmentMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.attachment.PendingAttachmentMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.poi.AuthorizedPOIMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.poi.PendingPOIMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.user.authenticated.AuthenticatedTouristMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AuthorizedAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.PendingAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.poi.AuthorizedPOIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.poi.PendingPOIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedTouristDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.AuthorizedAttachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.PendingAttachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.AuthorizedPOI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.PendingPOI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedTourist;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.poi.IPOIRepository;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.user.IAuthorizedUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Service
@AllArgsConstructor
public class POIService implements IPOIService {
    private final IPOIRepository _poiRepository;
    //TODO : CHANGE WITH AUTHENTICATEDUSERSERVICE
    private final IAuthorizedUserRepository _authenticatedUserRepository;
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
            _poiRepository.save(poi);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean deletePendingPOIById(UUID id) {
        if (getPOIById(id, PendingPOI.class) != null) {
            _poiRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteAuthorizedPOIById(UUID id) {
        if (getPOIById(id, AuthorizedPOI.class) != null) {
            _poiRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deletePendingPOI(PendingPOIDto poiDto, Optional<Predicate<POI>> predicate) {
        if (getPOIs(predicate, PendingPOI.class).get(0) != null) {
            PendingPOI poi = PendingPOIMapper.toEntity(poiDto);
            assert poi != null;
            _poiRepository.delete(poi);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteAuthorizedPOI(AuthorizedPOIDto poiDto, Optional<Predicate<POI>> predicate) {
        if (getPOIs(predicate, AuthorizedPOI.class).get(0) != null) {
            AuthorizedPOI poi = AuthorizedPOIMapper.toEntity(poiDto);
            assert poi != null;
            _poiRepository.delete(poi);
            return true;
        } else {
            return false;
        }
    }

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
    public boolean savePendingPOI(PendingPOIDto poiDto, AuthenticatedTouristDto touristDto) {
        if (_authenticatedUserRepository.existsById(touristDto.getId())) {
            PendingPOI poi = PendingPOIMapper.toEntity(poiDto);
            AuthenticatedTourist tourist = AuthenticatedTouristMapper.toEntity(touristDto);
            tourist.getPois().add(poi);
            _authenticatedUserRepository.save(tourist);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean saveAuthorizedPOI(AuthorizedPOIDto poiDto, AuthenticatedTouristDto touristDto) {
        if (_authenticatedUserRepository.existsById(touristDto.getId())) {
            AuthorizedPOI poi = AuthorizedPOIMapper.toEntity(poiDto);
            AuthenticatedTourist tourist = AuthenticatedTouristMapper.toEntity(touristDto);
            tourist.getPois().add(poi);
            _authenticatedUserRepository.save(tourist);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean addPendingAttachment(PendingAttachmentDto attachmentDto, Optional<Predicate<POI>> predicate) {
        POI poi = getPOIs(predicate, POI.class).get(0);
        if (poi != null) {
            PendingAttachment attachment = PendingAttachmentMapper.toEntity(attachmentDto);
            poi.getAttachments().add(attachment);
            _poiRepository.save(poi);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean addAuthorizedAttachment(AuthorizedAttachmentDto attachmentDto, Optional<Predicate<POI>> predicate) {
        POI poi = getPOIs(predicate, POI.class).get(0);
        if (poi != null) {
            AuthorizedAttachment attachment = AuthorizedAttachmentMapper.toEntity(attachmentDto);
            poi.getAttachments().add(attachment);
            _poiRepository.save(poi);
            return true;
        } else {
            return false;
        }
    }
}

