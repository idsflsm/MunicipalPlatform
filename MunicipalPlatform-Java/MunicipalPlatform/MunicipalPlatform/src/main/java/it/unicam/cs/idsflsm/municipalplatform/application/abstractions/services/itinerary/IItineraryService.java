package it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.itinerary;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AuthorizedAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.PendingAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.itinerary.AuthorizedItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.itinerary.PendingItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedTouristDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Itinerary;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
public interface IItineraryService {
    List<PendingItineraryDto> getAllPendingItineraries(Optional<Predicate<Itinerary>> predicate);
    List<AuthorizedItineraryDto> getAllAuthorizedItineraries(Optional<Predicate<Itinerary>> predicate);
    PendingItineraryDto getPendingItineraryById(UUID id);
    AuthorizedItineraryDto getAuthorizedItineraryById(UUID id);
    boolean addPendingItinerary(PendingItineraryDto itineraryDto);
    boolean addAuthorizedItinerary(AuthorizedItineraryDto itineraryDto);
    boolean deletePendingItineraryById(UUID id);
    boolean deleteAuthorizedItineraryById(UUID id);
    boolean deletePendingItinerary(PendingItineraryDto itineraryDto, Optional<Predicate<Itinerary>> predicate);
    boolean deleteAuthorizedItinerary(AuthorizedItineraryDto itineraryDto, Optional<Predicate<Itinerary>> predicate);
    boolean updatePendingItinerary(PendingItineraryDto itineraryDto, Optional<Predicate<Itinerary>> predicate);
    boolean updateAuthorizedItinerary(AuthorizedItineraryDto itineraryDto, Optional<Predicate<Itinerary>> predicate);
    boolean savePendingItinerary(PendingItineraryDto itineraryDto, AuthenticatedTouristDto touristDto);
    boolean saveAuthorizedItinerary(AuthorizedItineraryDto itineraryDto, AuthenticatedTouristDto touristDto);

    boolean addPendingAttachment(PendingAttachmentDto attachmentDto, Optional<Predicate<Itinerary>> predicate);
    boolean addAuthorizedAttachment(AuthorizedAttachmentDto attachmentDto, Optional<Predicate<Itinerary>> predicate);
}
