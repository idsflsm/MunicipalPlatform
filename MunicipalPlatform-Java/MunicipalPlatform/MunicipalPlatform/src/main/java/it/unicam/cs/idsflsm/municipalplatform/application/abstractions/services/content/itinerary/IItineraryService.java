package it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.content.itinerary;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AuthorizedAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.PendingAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.AuthorizedItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.ItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.PendingItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedUserDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserPermission;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
/**
 * Interface for Itinerary service class. It provides methods to manipulate persistent
 * itineraries in the database
 */
public interface IItineraryService {
    void saveInRepository(Itinerary itinerary);
    List<PendingItineraryDto> getAllPendingItineraries(Optional<Predicate<Itinerary>> predicate);
    List<AuthorizedItineraryDto> getAllAuthorizedItineraries(Optional<Predicate<Itinerary>> predicate);

    /**
     * Method that retrieves a list of Itinerary DTOs based on a given predicate
     * @param predicate an Optional Predicate<Itinerary> that can be used to filter the itineraries
     * @return the list of Itinerary DTOs. If the predicate is present, the list will only contain
     * itineraries that satisfy the predicate. If no itineraries satisfy the predicate or if the
     * repository contains no itineraries, an empty list will be returned
     */
    List<ItineraryDto> getItineraries(Optional<Predicate<Itinerary>> predicate);

    PendingItineraryDto getPendingItineraryById(UUID id);
    AuthorizedItineraryDto getAuthorizedItineraryById(UUID id);

    /**
     * Method that retrieves an Itinerary DTO by its unique identifier
     * @param id the UUID of the itinerary to be retrieved
     * @return an Itinerary DTO if an itinerary with the given UUID exists,
     * null otherwise
     */
    ItineraryDto getItineraryById(UUID id);

    PendingItineraryDto addPendingItinerary(PendingItineraryDto itineraryDto);
    AuthorizedItineraryDto addAuthorizedItinerary(AuthorizedItineraryDto itineraryDto);

    /**
     * Method that adds an Itinerary entity to the platform
     * @param itineraryDto the Itinerary DTO representing the entity to be added
     * @param permission the user's permission to create the correct type of Itinerary entity
     * @return the Itinerary DTO of the added entity
     */
    ItineraryDto addItinerary(ItineraryDto itineraryDto, UserPermission permission);

//    boolean deletePendingItineraryById(UUID id);
//    boolean deleteAuthorizedItineraryById(UUID id);
//    boolean deletePendingItinerary(PendingItineraryDto itineraryDto, Optional<Predicate<Itinerary>> predicate);
//    boolean deleteAuthorizedItinerary(AuthorizedItineraryDto itineraryDto, Optional<Predicate<Itinerary>> predicate);

    /**
     * Method that deletes an Itinerary entity by its unique identifier
     * and returns the deleted itinerary
     * @param id the UUID of the itinerary to be deleted
     * @return an Itinerary DTO if an itinerary with the given UUID exists and was deleted,
     * null otherwise
     */
    ItineraryDto deleteItineraryById(UUID id);

    PendingItineraryDto updatePendingItinerary(PendingItineraryDto itineraryDto);
    AuthorizedItineraryDto updateAuthorizedItinerary(AuthorizedItineraryDto itineraryDto);

    /**
     * Method that updates an existing Itinerary entity based on the provided Itinerary DTO
     * and returns the updated itinerary
     * @param itineraryDto the Itinerary DTO containing the updated information of the itinerary
     * @return an Itinerary DTO that represents the updated itinerary
     */
    ItineraryDto updateItinerary(ItineraryDto itineraryDto);

    PendingItineraryDto validatePendingItinerary(Optional<Predicate<Itinerary>> predicate, boolean validate);

    /**
     * Method that validates an Itinerary entity based on a provided predicate and a validation flag
     * @param predicate the Predicate<Itinerary> used to filter the itineraries
     * @param validate boolean flag indicating whether the itinerary should be validated or not
     * @return an Itinerary DTO if an itinerary satisfying the predicate exists,
     * null otherwise
     */
    ItineraryDto validateItinerary(Predicate<Itinerary> predicate, boolean validate);

    PendingItineraryDto savePendingItinerary(UUID id, AuthenticatedUserDto userDto);
    AuthorizedItineraryDto saveAuthorizedItinerary(UUID id, AuthenticatedUserDto userDto);

    /**
     * Method that saves an Itinerary entity to a user's favorites itineraries
     * @param id the unique identifier of the Itinerary entity to be saved
     * @param userDto the AuthenticatedUser DTO of the user that performs saving operation
     * @return the Itinerary DTO of the entity if it is saved,
     * null otherwise
     */
    ItineraryDto saveItinerary(UUID id, AuthenticatedUserDto userDto);

    List<PendingItineraryDto> uploadAllPendingItineraries();
    PendingItineraryDto uploadPendingItinerary(UUID id);
    List<AuthorizedItineraryDto> uploadAllAuthorizedItineraries();
    AuthorizedItineraryDto uploadAuthorizedItinerary(UUID id);

    /**
     * Method that uploads an Itinerary entity by its unique identifier
     * and returns the uploaded itinerary
     * @param id the UUID of the itinerary to be uploaded
     * @return an Itinerary DTO if an itinerary with the given UUID exists and is in UPLOADABLE state,
     * null otherwise
     */
    ItineraryDto uploadItineraryById(UUID id);

    AttachmentDto addPendingAttachment(PendingAttachmentDto attachmentDto, Optional<Predicate<Itinerary>> predicate);
    AttachmentDto addAuthorizedAttachment(AuthorizedAttachmentDto attachmentDto, Optional<Predicate<Itinerary>> predicate);

    /**
     * Method that adds an Attachment entity, for a particular itinerary, to the platform
     * @param attachmentDto the Attachment DTO representing the entity to be added
     * @param permission the user's permission to create the correct type of Attachment entity
     * @param predicate the predicate for filtering itineraries
     * @return the Attachment DTO of the entity if it is added,
     * null otherwise
     */
    AttachmentDto addAttachment(AttachmentDto attachmentDto, UserPermission permission, Predicate<Itinerary> predicate);

}
