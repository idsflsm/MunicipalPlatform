package it.unicam.cs.idsflsm.municipalplatform.application.services.attachment;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.handlers.attachment.IAttachmentHandler;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.attachment.IAttachmentService;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.attachment.GenericAttachmentMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.report.ReportMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.report.ReportDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.report.Report;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserPermission;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.attachment.IAttachmentRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;
/**
 * Service class for the AttachmentRepository. It provides methods to manipulate persistent
 * attachments in the database
 */
@Service
@Transactional
@AllArgsConstructor(onConstructor_ = @Autowired)
public class AttachmentService implements IAttachmentService {
    /**
     * The repository for Attachment entity
     */
    private final IAttachmentRepository _attachmentRepository;
    /**
     * The handler for Attachment entity
     */
    private final IAttachmentHandler _attachmentHandler;
    @Override
    public void saveInRepository(Attachment attachment) {
        _attachmentRepository.save(attachment);
    }
    @Override
    public List<AttachmentDto> getAttachments(Optional<Predicate<Attachment>> predicate) {
        Stream<Attachment> attachmentStream = _attachmentRepository.findAll().stream();
        List<Attachment> attachments = predicate.map(attachmentPredicate -> attachmentStream
                        .filter(attachmentPredicate)
                        .toList())
                .orElseGet(attachmentStream::toList);
        if (!attachments.isEmpty()) {
            return GenericAttachmentMapper.toDto(attachments, true);
        }
        return new ArrayList<>();
    }
    @Override
    public AttachmentDto getAttachmentById(UUID id) {
        Attachment attachment = _attachmentRepository.findById(id).orElse(null);
        if (attachment != null) {
            return GenericAttachmentMapper.toDto(attachment, true);
        }
        return null;
    }
    //    TODO : FOR TESTING PURPOSES ONLY
    @Override
    public AttachmentDto deleteAttachmentById(UUID id) {
        Attachment attachment = _attachmentRepository.findById(id).orElse(null);
        if (attachment != null) {
            attachment.detachFromEntities();
            _attachmentRepository.delete(attachment);
            return GenericAttachmentMapper.toDto(attachment, true);
        }
        return null;
    }
    @Override
    public AttachmentDto updateAttachment(AttachmentDto attachmentDto) {
        Attachment attachment = _attachmentHandler.modifyAttachment(attachmentDto, UserPermission.CURATOR_ATTACHMENT_UPDATE);
        _attachmentRepository.save(attachment);
        return GenericAttachmentMapper.toDto(attachment, true);
    }
    @Override
    public AttachmentDto validateAttachment(Predicate<Attachment> predicate, boolean validate) {
        List<Attachment> attachments = _attachmentRepository.findAll().stream().filter(predicate).toList();
        if (!attachments.isEmpty()) {
            Attachment attachment = attachments.get(0);
            ContentState newState = (validate) ? ContentState.UPLOADABLE : ContentState.REMOVABLE;
            attachment.setState(newState);
            _attachmentRepository.save(attachment);
            return GenericAttachmentMapper.toDto(attachment, true);
        }
        return null;
    }
    @Override
    public AttachmentDto uploadAttachmentById(UUID id) {
        Attachment attachment = _attachmentRepository.findById(id).orElse(null);
        if (attachment != null && attachment.getState().equals(ContentState.UPLOADABLE)) {
            attachment.setState(ContentState.UPLOADED);
            _attachmentRepository.save(attachment);
            return GenericAttachmentMapper.toDto(attachment, true);
        }
        return null;
    }
    @Override
    public ReportDto addReport(ReportDto reportDto, Predicate<Attachment> predicate) {
        List<Attachment> attachments = _attachmentRepository.findAll().stream().filter(predicate).toList();
        Attachment attachment = (!attachments.isEmpty()) ? attachments.get(0) : null;
        if (attachment != null) {
            Report report = ReportMapper.toEntity(reportDto, true);
            report.setAttachment(attachment);
            attachment.getReports().add(report);
            _attachmentRepository.save(attachment);
            return ReportMapper.toDto(report, true);
        }
        return null;
    }
}
