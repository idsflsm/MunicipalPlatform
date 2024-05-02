package it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.attachment;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AuthorizedAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.PendingAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.report.ReportDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Content;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.report.Report;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
public interface IAttachmentService {
    List<PendingAttachmentDto> getAllPendingAttachments(Optional<Predicate<Attachment>> predicate);
    List<AuthorizedAttachmentDto> getAllAuthorizedAttachments(Optional<Predicate<Attachment>> predicate);
    PendingAttachmentDto getPendingAttachmentById(UUID id);
    AuthorizedAttachmentDto getAuthorizedAttachmentById(UUID id);
    boolean addPendingAttachment(PendingAttachmentDto attachmentDto);
    boolean addAuthorizedAttachment(AuthorizedAttachmentDto attachmentDto);
    boolean deletePendingAttachmentById(UUID id);
    boolean deleteAuthorizedAttachmentById(UUID id);
    boolean deletePendingAttachment(PendingAttachmentDto attachmentDto, Optional<Predicate<Attachment>> predicate);
    boolean deleteAuthorizedAttachment(AuthorizedAttachmentDto attachmentDto, Optional<Predicate<Attachment>> predicate);
    boolean updatePendingAttachment(PendingAttachmentDto attachmentDto, Optional<Predicate<Attachment>> predicate);
    boolean updateAuthorizedAttachment(AuthorizedAttachmentDto attachmentDto, Optional<Predicate<Attachment>> predicate);

    boolean addReport(ReportDto reportDto, Optional<Predicate<Attachment>> predicate);
}
