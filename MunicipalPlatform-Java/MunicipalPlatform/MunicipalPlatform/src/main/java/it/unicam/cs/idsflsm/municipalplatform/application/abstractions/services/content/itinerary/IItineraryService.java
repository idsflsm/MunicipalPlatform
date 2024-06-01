package it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.content.itinerary;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AuthorizedAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.PendingAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.AuthorizedItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.PendingItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.AuthorizedPOIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.PendingPOIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedUserDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
public interface IItineraryService {
    void saveInRepository(Itinerary itinerary);
    List<PendingItineraryDto> getAllPendingItineraries(Optional<Predicate<Itinerary>> predicate);
    List<AuthorizedItineraryDto> getAllAuthorizedItineraries(Optional<Predicate<Itinerary>> predicate);
    PendingItineraryDto getPendingItineraryById(UUID id);
    AuthorizedItineraryDto getAuthorizedItineraryById(UUID id);
    PendingItineraryDto addPendingItinerary(PendingItineraryDto itineraryDto);
    AuthorizedItineraryDto addAuthorizedItinerary(AuthorizedItineraryDto itineraryDto);
//    boolean deletePendingItineraryById(UUID id);
    boolean deleteAuthorizedItineraryById(UUID id);
//    boolean deletePendingItinerary(PendingItineraryDto itineraryDto, Optional<Predicate<Itinerary>> predicate);
//    boolean deleteAuthorizedItinerary(AuthorizedItineraryDto itineraryDto, Optional<Predicate<Itinerary>> predicate);
    PendingItineraryDto updatePendingItinerary(PendingItineraryDto itineraryDto);
    AuthorizedItineraryDto updateAuthorizedItinerary(AuthorizedItineraryDto itineraryDto);
    PendingItineraryDto validatePendingItinerary(Optional<Predicate<Itinerary>> predicate, boolean validate);
    PendingItineraryDto savePendingItinerary(UUID id, AuthenticatedUserDto userDto);
    AuthorizedItineraryDto saveAuthorizedItinerary(UUID id, AuthenticatedUserDto userDto);
    List<PendingItineraryDto> uploadAllPendingItineraries();
    PendingItineraryDto uploadPendingItinerary(UUID id);
    List<AuthorizedItineraryDto> uploadAllAuthorizedItineraries();
    AuthorizedItineraryDto uploadAuthorizedItinerary(UUID id);

    AttachmentDto addPendingAttachment(PendingAttachmentDto attachmentDto, Optional<Predicate<Itinerary>> predicate);
    AttachmentDto addAuthorizedAttachment(AuthorizedAttachmentDto attachmentDto, Optional<Predicate<Itinerary>> predicate);
}
