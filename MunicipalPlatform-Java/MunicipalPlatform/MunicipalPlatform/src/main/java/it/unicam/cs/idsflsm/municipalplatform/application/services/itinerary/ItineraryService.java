package it.unicam.cs.idsflsm.municipalplatform.application.services.itinerary;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.itinerary.IItineraryService;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.attachment.AuthorizedAttachmentMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.attachment.PendingAttachmentMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.itinerary.AuthorizedItineraryMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.itinerary.PendingItineraryMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.user.authenticated.AuthenticatedTouristMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AuthorizedAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.PendingAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.itinerary.AuthorizedItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.itinerary.PendingItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedTouristDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.AuthorizedAttachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.PendingAttachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.AuthorizedItinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.PendingItinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedTourist;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.itinerary.IItineraryRepository;
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
public class ItineraryService implements IItineraryService {
    private final IItineraryRepository _itineraryRepository;
    //TODO : CHANGE WITH AUTHENTICATEDUSERSERVICE
    private final IAuthorizedUserRepository _authenticatedUserRepository;
    @Override
//TODO: RESOLVE THIS AND ASSURE THAT ISPENDINGITINERARY WORKS
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
            _itineraryRepository.save(itinerary);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean deletePendingItineraryById(UUID id) {
        if (getItineraryById(id, PendingItinerary.class) != null) {
            _itineraryRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteAuthorizedItineraryById(UUID id) {
        if (getItineraryById(id, AuthorizedItinerary.class) != null) {
            _itineraryRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deletePendingItinerary(PendingItineraryDto itineraryDto, Optional<Predicate<Itinerary>> predicate) {
        if (getItineraries(predicate, PendingItinerary.class).get(0) != null) {
            PendingItinerary itinerary = PendingItineraryMapper.toEntity(itineraryDto);
            assert itinerary != null;
            _itineraryRepository.delete(itinerary);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteAuthorizedItinerary(AuthorizedItineraryDto itineraryDto, Optional<Predicate<Itinerary>> predicate) {
        if (getItineraries(predicate, PendingItinerary.class).get(0) != null) {
            AuthorizedItinerary itinerary = AuthorizedItineraryMapper.toEntity(itineraryDto);
            assert itinerary != null;
            _itineraryRepository.delete(itinerary);
            return true;
        } else {
            return false;
        }
    }

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
    public boolean savePendingItinerary(PendingItineraryDto itineraryDto, AuthenticatedTouristDto touristDto) {
        if (_authenticatedUserRepository.existsById(touristDto.getId())) {
            PendingItinerary itinerary = PendingItineraryMapper.toEntity(itineraryDto);
            AuthenticatedTourist tourist = AuthenticatedTouristMapper.toEntity(touristDto);
            tourist.getItineraries().add(itinerary);
            _authenticatedUserRepository.save(tourist);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean saveAuthorizedItinerary(AuthorizedItineraryDto itineraryDto, AuthenticatedTouristDto touristDto) {
        if (_authenticatedUserRepository.existsById(touristDto.getId())) {
            AuthorizedItinerary itinerary = AuthorizedItineraryMapper.toEntity(itineraryDto);
            AuthenticatedTourist tourist = AuthenticatedTouristMapper.toEntity(touristDto);
            tourist.getItineraries().add(itinerary);
            _authenticatedUserRepository.save(tourist);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean addPendingAttachment(PendingAttachmentDto attachmentDto, Optional<Predicate<Itinerary>> predicate) {
        Itinerary itinerary = getItineraries(predicate, Itinerary.class).get(0);
        if (itinerary != null) {
            PendingAttachment attachment = PendingAttachmentMapper.toEntity(attachmentDto);
            itinerary.getAttachments().add(attachment);
            _itineraryRepository.save(itinerary);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean addAuthorizedAttachment(AuthorizedAttachmentDto attachmentDto, Optional<Predicate<Itinerary>> predicate) {
        Itinerary itinerary = getItineraries(predicate, Itinerary.class).get(0);
        if (itinerary != null) {
            AuthorizedAttachment attachment = AuthorizedAttachmentMapper.toEntity(attachmentDto);
            itinerary.getAttachments().add(attachment);
            _itineraryRepository.save(itinerary);
            return true;
        } else {
            return false;
        }
    }
}
