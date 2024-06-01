package it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.content.poi;

import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AuthorizedAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.PendingAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.AuthorizedPOIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.POIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.poi.PendingPOIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedUserDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public interface IPOIService {
    void saveInRepository(POI poi);
    List<PendingPOIDto> getAllPendingPOIs(Optional<Predicate<POI>> predicate);
    List<AuthorizedPOIDto> getAllAuthorizedPOIs(Optional<Predicate<POI>> predicate);
    List<POI> getAllPOIs(Optional<Predicate<POI>> predicate);
    PendingPOIDto getPendingPOIById(UUID id);
    AuthorizedPOIDto getAuthorizedPOIById(UUID id);
    POI getPOIById(UUID id);
    PendingPOIDto addPendingPOI(PendingPOIDto poiDto);
    AuthorizedPOIDto addAuthorizedPOI(AuthorizedPOIDto poiDto);
    boolean deletePendingPOIById(UUID id);
    boolean deleteAuthorizedPOIById(UUID id);
//    boolean deletePendingPOI(PendingPOIDto poiDto, Optional<Predicate<POI>> predicate);
//    boolean deleteAuthorizedPOI(AuthorizedPOIDto poiDto, Optional<Predicate<POI>> predicate);
    PendingPOIDto updatePendingPOI(PendingPOIDto poiDto);
    AuthorizedPOIDto updateAuthorizedPOI(AuthorizedPOIDto poiDto);
    PendingPOIDto validatePendingPOI(Optional<Predicate<POI>> predicate, boolean validate);
//    boolean savePendingPOI(PendingPOIDto poiDto, AuthenticatedTouristDto touristDto);
//    boolean savePendingPOI(PendingPOIDto poiDto, UUID idAuthenticatedTourist);
//   boolean saveAuthorizedPOI(AuthorizedPOIDto poiDto, AuthenticatedTouristDto touristDto);
//    boolean saveAuthorizedPOI(AuthorizedPOIDto poiDto, UUID idAuthenticatedTourist);
    PendingPOIDto savePendingPOI(UUID id, AuthenticatedUserDto userDto);
    AuthorizedPOIDto saveAuthorizedPOI(UUID id, AuthenticatedUserDto userDto);
    List<PendingPOIDto> uploadAllPendingPOIs();
    PendingPOIDto uploadPendingPOI(UUID id);
    List<AuthorizedPOIDto> uploadAllAuthorizedPOIs();
    AuthorizedPOIDto uploadAuthorizedPOI(UUID id);

    AttachmentDto addPendingAttachment(PendingAttachmentDto attachmentDto, Optional<Predicate<POI>> predicate);
    AttachmentDto addAuthorizedAttachment(AuthorizedAttachmentDto attachmentDto, Optional<Predicate<POI>> predicate);
}
