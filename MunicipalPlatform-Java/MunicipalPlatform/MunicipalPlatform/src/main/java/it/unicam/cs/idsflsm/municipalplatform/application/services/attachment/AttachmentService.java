package it.unicam.cs.idsflsm.municipalplatform.application.services.attachment;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.attachment.IAttachmentService;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.report.IReportService;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.attachment.AuthorizedAttachmentMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.attachment.PendingAttachmentMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.report.ReportMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AuthorizedAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.PendingAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.report.ReportDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.AuthorizedAttachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.PendingAttachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.report.Report;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.attachment.IAttachmentRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Service
@Transactional
@AllArgsConstructor(onConstructor_ = @Autowired)
public class AttachmentService implements IAttachmentService {
    private final IAttachmentRepository _attachmentRepository;
    private final IReportService _reportService;
    @Override
    public void saveInRepository(Attachment attachment) {
        _attachmentRepository.save(attachment);
    }
    @Override
    public List<PendingAttachmentDto> getAllPendingAttachments(Optional<Predicate<Attachment>> predicate) {
        List<PendingAttachment> attachments = getAttachments(predicate, PendingAttachment.class);
        if (!attachments.isEmpty()) {
            return PendingAttachmentMapper.toDto(attachments);
        } else {
            return null;
        }
    }
    @Override
    public List<AuthorizedAttachmentDto> getAllAuthorizedAttachments(Optional<Predicate<Attachment>> predicate) {
        List<AuthorizedAttachment> attachments = getAttachments(predicate, AuthorizedAttachment.class);
        if (!attachments.isEmpty()) {
            return AuthorizedAttachmentMapper.toDto(attachments);
        } else {
            return null;
        }
    }
    private <T extends Attachment> List<T> getAttachments(Optional<Predicate<Attachment>> predicate, Class<T> type) {
        Stream<Attachment> attachments = _attachmentRepository.findAll().stream();
        return predicate.map(attachmentPredicate -> attachments.filter(attachmentPredicate.and((type::isInstance)))
                        .map(type::cast)
                        .collect(Collectors.toList()))
                .orElseGet(() -> attachments.filter(type::isInstance)
                        .map(type::cast)
                        .collect(Collectors.toList()));
    }
    @Override
    public PendingAttachmentDto getPendingAttachmentById(UUID id) {
        return PendingAttachmentMapper.toDto(getAttachmentById(id, PendingAttachment.class));
    }
    @Override
    public AuthorizedAttachmentDto getAuthorizedAttachmentById(UUID id) {
        return AuthorizedAttachmentMapper.toDto(getAttachmentById(id, AuthorizedAttachment.class));
    }
    private <T extends Attachment> T getAttachmentById(UUID id, Class<T> type) {
        Attachment attachment = _attachmentRepository.findById(id).orElse(null);
        if (attachment != null) {
            return (attachment.getClass() == type) ? type.cast(attachment) : null;
        } else {
            return null;
        }
    }
    // TODO : USELESS TO HAVE IT HERE
    @Override
    public boolean addPendingAttachment(PendingAttachmentDto attachmentDto) {
        // TODO : USEFUL CHECK ONLY FOR DUPLICATED IDs (PRETTY USELESS)
        if (getAttachmentById(attachmentDto.getId(), PendingAttachment.class) == null) {
            PendingAttachment attachment = PendingAttachmentMapper.toEntity(attachmentDto);
            _attachmentRepository.save(attachment);
            return true;
        } else {
            return false;
        }
    }
    // TODO : USELESS TO HAVE IT HERE
    @Override
    public boolean addAuthorizedAttachment(AuthorizedAttachmentDto attachmentDto) {
        // TODO : USEFUL CHECK ONLY FOR DUPLICATED IDs (PRETTY USELESS)
        if (getAttachmentById(attachmentDto.getId(), AuthorizedAttachment.class) == null) {
            AuthorizedAttachment attachment = AuthorizedAttachmentMapper.toEntity(attachmentDto);
            _attachmentRepository.save(attachment);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean deletePendingAttachmentById(UUID id) {
        PendingAttachment attachment = getAttachmentById(id, PendingAttachment.class);
        if (attachment != null) {
            attachment.setState(ContentState.REMOVABLE);
            // _attachmentRepository.deleteById(id);
            _attachmentRepository.save(attachment);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean deleteAuthorizedAttachmentById(UUID id) {
        AuthorizedAttachment attachment = getAttachmentById(id, AuthorizedAttachment.class);
        if (attachment != null) {
            attachment.setState(ContentState.REMOVABLE);
            // _attachmentRepository.deleteById(id);
            _attachmentRepository.save(attachment);
            return true;
        } else {
            return false;
        }
    }
//    // TODO : PROBABLY USELESS
//    @Override
//    public boolean deletePendingAttachment(PendingAttachmentDto attachmentDto, Optional<Predicate<Attachment>> predicate) {
//        if (getAttachments(predicate, PendingAttachment.class).get(0) != null) {
//            PendingAttachment attachment = PendingAttachmentMapper.toEntity(attachmentDto);
//            assert attachment != null;
//            attachment.setState(ContentState.Removable);
//            // _attachmentRepository.delete(attachment);
//            _attachmentRepository.save(attachment);
//            return true;
//        } else {
//            return false;
//        }
//    }
//    // TODO : PROBABLY USELESS
//    @Override
//    public boolean deleteAuthorizedAttachment(AuthorizedAttachmentDto attachmentDto, Optional<Predicate<Attachment>> predicate) {
//        if (getAttachments(predicate, PendingAttachment.class).get(0) != null) {
//            AuthorizedAttachment attachment = AuthorizedAttachmentMapper.toEntity(attachmentDto);
//            assert attachment != null;
//            attachment.setState(ContentState.Removable);
//            // _attachmentRepository.delete(attachment);
//            _attachmentRepository.save(attachment);
//            return true;
//        } else {
//            return false;
//        }
//    }
    @Override
    public boolean updatePendingAttachment(PendingAttachmentDto attachmentDto, Optional<Predicate<Attachment>> predicate) {
        if (getAttachments(predicate, PendingAttachment.class).get(0) != null) {
            PendingAttachment attachment = PendingAttachmentMapper.toEntity(attachmentDto);
            assert attachment != null;
            _attachmentRepository.save(attachment);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean updateAuthorizedAttachment(AuthorizedAttachmentDto attachmentDto, Optional<Predicate<Attachment>> predicate) {
        if (getAttachments(predicate, AuthorizedAttachment.class).get(0) != null) {
            AuthorizedAttachment attachment = AuthorizedAttachmentMapper.toEntity(attachmentDto);
            assert attachment != null;
            _attachmentRepository.save(attachment);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean validatePendingAttachment(PendingAttachmentDto attachmentDto, Optional<Predicate<Attachment>> predicate, boolean validate) {
        PendingAttachment attachment = PendingAttachmentMapper.toEntity(attachmentDto);
        assert attachment != null;
        if (getAttachments(predicate, PendingAttachment.class).get(0) != null) {
            ContentState newState = (validate) ? ContentState.UPLOADABLE : ContentState.REMOVABLE;
            attachment.setState(newState);
            _attachmentRepository.save(attachment);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean addReport(ReportDto reportDto, Optional<Predicate<Attachment>> predicate) {
        Attachment attachment = getAttachments(predicate, Attachment.class).get(0);
        if (attachment != null) {
            Report report = ReportMapper.toEntity(reportDto);
            report.setAttachment(attachment);
            attachment.getReports().add(report);
            _attachmentRepository.save(attachment);
            _reportService.saveInRepository(report);
            return true;
        } else {
            return false;
        }
    }
}
