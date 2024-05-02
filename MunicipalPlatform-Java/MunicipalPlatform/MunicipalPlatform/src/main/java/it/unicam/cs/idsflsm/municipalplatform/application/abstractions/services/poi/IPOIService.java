package it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.poi;

import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AuthorizedAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.PendingAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.poi.AuthorizedPOIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.poi.PendingPOIDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedTouristDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.POI;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public interface IPOIService {
    List<PendingPOIDto> getAllPendingPOIs(Optional<Predicate<POI>> predicate);
    List<AuthorizedPOIDto> getAllAuthorizedPOIs(Optional<Predicate<POI>> predicate);
    PendingPOIDto getPendingPOIById(UUID id);
    AuthorizedPOIDto getAuthorizedPOIById(UUID id);
    boolean addPendingPOI(PendingPOIDto poiDto);
    boolean addAuthorizedPOI(AuthorizedPOIDto poiDto);
    boolean deletePendingPOIById(UUID id);
    boolean deleteAuthorizedPOIById(UUID id);
    boolean deletePendingPOI(PendingPOIDto poiDto, Optional<Predicate<POI>> predicate);
    boolean deleteAuthorizedPOI(AuthorizedPOIDto poiDto, Optional<Predicate<POI>> predicate);
    boolean updatePendingPOI(PendingPOIDto poiDto, Optional<Predicate<POI>> predicate);
    boolean updateAuthorizedPOI(AuthorizedPOIDto poiDto, Optional<Predicate<POI>> predicate);
    boolean savePendingPOI(PendingPOIDto poiDto, AuthenticatedTouristDto touristDto);
    boolean saveAuthorizedPOI(AuthorizedPOIDto poiDto, AuthenticatedTouristDto touristDto);

    boolean addPendingAttachment(PendingAttachmentDto attachmentDto, Optional<Predicate<POI>> predicate);
    boolean addAuthorizedAttachment(AuthorizedAttachmentDto attachmentDto, Optional<Predicate<POI>> predicate);
}
