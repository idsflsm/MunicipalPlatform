package it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.attachment;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AuthorizedAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.PendingAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.report.ReportDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
public interface IAttachmentService {
    void saveInRepository(Attachment attachment);
    void deleteFromRepository(Attachment attachment);
    List<PendingAttachmentDto> getAllPendingAttachments(Optional<Predicate<Attachment>> predicate);
    List<AuthorizedAttachmentDto> getAllAuthorizedAttachments(Optional<Predicate<Attachment>> predicate);
    PendingAttachmentDto getPendingAttachmentById(UUID id);
    AuthorizedAttachmentDto getAuthorizedAttachmentById(UUID id);
//    PendingAttachmentDto addPendingAttachment(PendingAttachmentDto attachmentDto);
//    AuthorizedAttachmentDto addAuthorizedAttachment(AuthorizedAttachmentDto attachmentDto);
//    boolean deletePendingAttachmentById(UUID id);
    boolean deleteAuthorizedAttachmentById(UUID id);
    PendingAttachmentDto updatePendingAttachment(PendingAttachmentDto attachmentDto, Optional<Predicate<Attachment>> predicate);
    AuthorizedAttachmentDto updateAuthorizedAttachment(AuthorizedAttachmentDto attachmentDto, Optional<Predicate<Attachment>> predicate);
    PendingAttachmentDto validatePendingAttachment(Optional<Predicate<Attachment>> predicate, boolean validate);
    List<PendingAttachmentDto> uploadAllPendingAttachments();
    PendingAttachmentDto uploadPendingAttachment(UUID id);
    List<AuthorizedAttachmentDto> uploadAllAuthorizedAttachments();
    AuthorizedAttachmentDto uploadAuthorizedAttachment(UUID id);

    ReportDto addReport(ReportDto reportDto, Optional<Predicate<Attachment>> predicate);
}
