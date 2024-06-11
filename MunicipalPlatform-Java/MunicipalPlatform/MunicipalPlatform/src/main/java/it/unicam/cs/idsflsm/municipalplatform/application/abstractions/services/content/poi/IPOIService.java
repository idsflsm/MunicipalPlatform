package it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.content.poi;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.POIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedUserDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserPermission;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
/**
 * Interface for POI service class. It provides methods to manipulate persistent
 * POIs in the database
 */
public interface IPOIService {
    void saveInRepository(POI poi);
    /**
     * Method that retrieves a list of POI DTOs based on a given predicate
     *
     * @param predicate an Optional Predicate<POI> that can be used to filter the pois
     * @return the list of POI DTOs. If the predicate is present, the list will only contain
     * pois that satisfy the predicate. If no pois satisfy the predicate or if the
     * repository contains no pois, an empty list will be returned
     */
    List<POIDto> getPOIs(Optional<Predicate<POI>> predicate);
    /**
     * Method that retrieves a POI DTO by its unique identifier
     *
     * @param id the UUID of the poi to be retrieved
     * @return a POI DTO if a poi with the given UUID exists,
     * null otherwise
     */
    POIDto getPOIById(UUID id);
    /**
     * Method that adds a POI entity to the platform
     *
     * @param poiDto     the POI DTO representing the entity to be added
     * @param permission the user's permission to create the correct type of POI entity
     * @return the POI DTO of the added entity
     */
    POIDto addPOI(POIDto poiDto, UserPermission permission);
    /**
     * Method that deletes a POI entity by its unique identifier
     * and returns the deleted poi
     *
     * @param id the UUID of the poi to be deleted
     * @return a POI DTO if a poi with the given UUID exists and was deleted,
     * null otherwise
     */
    POIDto deletePOIById(UUID id);
    /**
     * Method that updates an existing POI entity based on the provided POI DTO
     * and returns the updated poi
     *
     * @param poiDto the POI DTO containing the updated information of the poi
     * @return a POI DTO that represents the updated poi
     */
    POIDto updatePOI(POIDto poiDto);
    /**
     * Method that validates a POI entity based on a provided predicate and a validation flag
     *
     * @param predicate the Predicate<POI> used to filter the pois
     * @param validate  boolean flag indicating whether the poi should be validated or not
     * @return a POI DTO if a poi satisfying the predicate exists,
     * null otherwise
     */
    POIDto validatePOI(Predicate<POI> predicate, boolean validate);
    /**
     * Method that saves a POI entity to a user's favorites pois
     *
     * @param id      the unique identifier of the POI entity to be saved
     * @param userDto the AuthenticatedUser DTO of the user that performs saving operation
     * @return the POI DTO of the entity if it is saved,
     * null otherwise
     */
    POIDto savePOI(UUID id, AuthenticatedUserDto userDto);
    /**
     * Method that uploads a POI entity by its unique identifier
     * and returns the uploaded poi
     *
     * @param id the UUID of the poi to be uploaded
     * @return a POI DTO if a poi with the given UUID exists and is in UPLOADABLE state,
     * null otherwise
     */
    POIDto uploadPOIById(UUID id);
    /**
     * Method that adds an Attachment entity, for a particular itinerary, to the platform
     *
     * @param attachmentDto the Attachment DTO representing the entity to be added
     * @param permission    the user's permission to create the correct type of Attachment entity
     * @param predicate     the predicate for filtering itineraries
     * @return the Attachment DTO of the entity if it is added,
     * null otherwise
     */
    AttachmentDto addAttachment(AttachmentDto attachmentDto, UserPermission permission, Predicate<POI> predicate);
}
