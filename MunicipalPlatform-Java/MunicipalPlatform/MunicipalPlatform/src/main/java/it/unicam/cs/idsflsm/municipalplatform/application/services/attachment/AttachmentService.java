package it.unicam.cs.idsflsm.municipalplatform.application.services.attachment;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.handlers.attachment.IAttachmentHandler;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.attachment.IAttachmentService;
import it.unicam.cs.idsflsm.municipalplatform.application.criterias.attachment.AttachmentCriteria;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.attachment.AuthorizedAttachmentMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.attachment.GenericAttachmentMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.attachment.PendingAttachmentMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.report.ReportMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AuthorizedAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.PendingAttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.report.ReportDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.AuthorizedAttachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.PendingAttachment;
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
//    private final AttachmentBuilderFactory _attachmentBuilderFactory;
    /**
     * The handler for Attachment entity
     */
    private final IAttachmentHandler _attachmentHandler;
//    private final IReportService _reportService;
    @Override
    public void saveInRepository(Attachment attachment) {
        _attachmentRepository.save(attachment);
    }
    @Override
    public void deleteFromRepository(Attachment attachment) {
        _attachmentRepository.delete(attachment);
    }
    @Override
    public List<PendingAttachmentDto> getAllPendingAttachments(Optional<Predicate<Attachment>> predicate) {
        List<Attachment> attachments = getAttachments(predicate, PendingAttachment.class);
        if (!attachments.isEmpty()) {
            return GenericAttachmentMapper.toDto(attachments, true).stream()
                    .map(attachmentDto -> (PendingAttachmentDto) attachmentDto)
                    .toList();
        } else {
            return null;
        }
    }
    @Override
    public List<AuthorizedAttachmentDto> getAllAuthorizedAttachments(Optional<Predicate<Attachment>> predicate) {
        List<Attachment> attachments = getAttachments(predicate, AuthorizedAttachment.class);
        if (!attachments.isEmpty()) {
            return GenericAttachmentMapper.toDto(attachments, true).stream()
                    .map(attachmentDto -> (AuthorizedAttachmentDto) attachmentDto)
                    .toList();
        } else {
            return null;
        }
    }


//    TODO : MERGING OF PENDING AND AUTHORIZED
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


    private List<Attachment> getAttachments(Optional<Predicate<Attachment>> predicate, Class<?> type) {
        Stream<Attachment> attachments = _attachmentRepository.findAll().stream();
        return predicate.map(attachmentPredicate -> attachments
                        .filter(attachmentPredicate.and((type::isInstance)))
                        .toList())
                .orElseGet(() -> attachments
                        .filter(type::isInstance)
                        .toList());
    }
    @Override
    public PendingAttachmentDto getPendingAttachmentById(UUID id) {
        return PendingAttachmentMapper.toDto(getAttachmentById(id, PendingAttachment.class), true);
    }
    @Override
    public AuthorizedAttachmentDto getAuthorizedAttachmentById(UUID id) {
        return AuthorizedAttachmentMapper.toDto(getAttachmentById(id, AuthorizedAttachment.class), true);
    }


//    TODO : MERGING OF PENDING AND AUTHORIZED
    @Override
    public AttachmentDto getAttachmentById(UUID id) {
        Attachment attachment = _attachmentRepository.findById(id).orElse(null);
        if (attachment != null) {
            return GenericAttachmentMapper.toDto(attachment, true);
        }
        return null;
    }


    private <T extends Attachment> T getAttachmentById(UUID id, Class<T> type) {
        Attachment attachment = _attachmentRepository.findById(id).orElse(null);
        if (attachment != null) {
            return (attachment.getClass() == type) ? type.cast(attachment) : null;
        } else {
            return null;
        }
    }
//    // TODO : USELESS TO HAVE IT HERE
//    @Override
//    public PendingAttachmentDto addPendingAttachment(PendingAttachmentDto attachmentDto) {
//        // TODO : USEFUL CHECK ONLY FOR DUPLICATED IDs (PRETTY USELESS)
//        if (getAttachmentById(attachmentDto.getId(), PendingAttachment.class) == null) {
//            PendingAttachment attachment = PendingAttachmentMapper.toEntity(attachmentDto, true);
//            _attachmentRepository.save(attachment);
//            return attachmentDto;
//        } else {
//            return null;
//        }
//    }
//    // TODO : USELESS TO HAVE IT HERE
//    @Override
//    public AuthorizedAttachmentDto addAuthorizedAttachment(AuthorizedAttachmentDto attachmentDto) {
//        // TODO : USEFUL CHECK ONLY FOR DUPLICATED IDs (PRETTY USELESS)
//        if (getAttachmentById(attachmentDto.getId(), AuthorizedAttachment.class) == null) {
//            AuthorizedAttachment attachment = AuthorizedAttachmentMapper.toEntity(attachmentDto, true);
//            _attachmentRepository.save(attachment);
//            return attachmentDto;
//        } else {
//            return null;
//        }
//    }
//    @Override
//    public boolean deletePendingAttachmentById(UUID id) {
//        PendingAttachment attachment = getAttachmentById(id, PendingAttachment.class);
//        if (attachment != null) {
//            attachment.setState(ContentState.REMOVABLE);
//            // _attachmentRepository.deleteById(id);
//            _attachmentRepository.save(attachment);
//            return true;
//        } else {
//            return false;
//        }
//    }
//    @Override
//    public boolean deleteAuthorizedAttachmentById(UUID id) {
//        AuthorizedAttachment attachment = getAttachmentById(id, AuthorizedAttachment.class);
//        if (attachment != null) {
////            attachment.setState(ContentState.REMOVABLE);
////            // _attachmentRepository.deleteById(id);
////            _attachmentRepository.save(attachment);
////            attachment.getPoi().getAttachments().remove(attachment);
////            attachment.setPoi(null);
//            attachment.detachFromEntities();
//            _attachmentRepository.delete(attachment);
//            return true;
//        } else {
//            return false;
//        }
//    }


//    TODO : MERGING OF PENDING AND AUTHORIZED
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
    public PendingAttachmentDto updatePendingAttachment(PendingAttachmentDto attachmentDto, Optional<Predicate<Attachment>> predicate) {
        var attachment = _attachmentHandler.modifyAttachment(attachmentDto, UserPermission.CURATOR_ATTACHMENT_UPDATE);
//      TODO : THIS WAS CORRECT
//        _attachmentRepository.save(attachment);
        return attachmentDto;
    }
    @Override
    public AuthorizedAttachmentDto updateAuthorizedAttachment(AuthorizedAttachmentDto attachmentDto, Optional<Predicate<Attachment>> predicate) {
        var attachment = _attachmentHandler.modifyAttachment(attachmentDto, UserPermission.CURATOR_ATTACHMENT_UPDATE);
//      TODO : THIS WAS CORRECT
//        _attachmentRepository.save(attachment);
        return attachmentDto;
    }


//    TODO : MERGING OF PENDING AND AUTHORIZED
    @Override
    public AttachmentDto updateAttachment(AttachmentDto attachmentDto) {
        Attachment attachment = _attachmentHandler.modifyAttachment(attachmentDto, UserPermission.CURATOR_ATTACHMENT_UPDATE);
        _attachmentRepository.save(attachment);
        return GenericAttachmentMapper.toDto(attachment, true);
    }


    @Override
    public PendingAttachmentDto validatePendingAttachment(Optional<Predicate<Attachment>> predicate, boolean validate) {
        PendingAttachment attachment = (PendingAttachment) getAttachments(predicate, PendingAttachment.class).get(0);
        if (attachment != null) {
            ContentState newState = (validate) ? ContentState.UPLOADABLE : ContentState.REMOVABLE;
            attachment.setState(newState);
            _attachmentRepository.save(attachment);
            return PendingAttachmentMapper.toDto(attachment, true);
        } else {
            return null;
        }
    }


//    TODO : MERGING OF PENDING AND AUTHORIZED
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
    public List<PendingAttachmentDto> uploadAllPendingAttachments() {
        List<Attachment> attachments = getAttachments(Optional.of(AttachmentCriteria.criteriaBuilder(AttachmentCriteria.isPendingAttachment(), AttachmentCriteria.isInUploadableState())), PendingAttachment.class);
        attachments = attachments.stream().peek(attachment -> {
            attachment.setState(ContentState.UPLOADED);
            _attachmentRepository.save(attachment);
        })
        .toList();
        return GenericAttachmentMapper.toDto(attachments, true).stream().map(attachmentDto -> (PendingAttachmentDto) attachmentDto).toList();
    }
    @Override
    public PendingAttachmentDto uploadPendingAttachment(UUID id) {
        PendingAttachment attachment = getAttachmentById(id, PendingAttachment.class);
        if (attachment != null && attachment.getState().equals(ContentState.UPLOADABLE)) {
            attachment.setState(ContentState.UPLOADED);
            _attachmentRepository.save(attachment);
            return PendingAttachmentMapper.toDto(attachment, true);
        } else {
            return null;
        }
    }
    @Override
    public List<AuthorizedAttachmentDto> uploadAllAuthorizedAttachments() {
        List<Attachment> attachments = getAttachments(Optional.of(AttachmentCriteria.criteriaBuilder(AttachmentCriteria.isAuthorizedAttachment(), AttachmentCriteria.isInUploadableState())), AuthorizedAttachment.class);
        attachments = attachments.stream().peek(attachment -> {
            attachment.setState(ContentState.UPLOADED);
            _attachmentRepository.save(attachment);
        }).toList();
        return GenericAttachmentMapper.toDto(attachments, true).stream().map(attachmentDto -> (AuthorizedAttachmentDto) attachmentDto).toList();
    }
    @Override
    public AuthorizedAttachmentDto uploadAuthorizedAttachment(UUID id) {
        AuthorizedAttachment attachment = getAttachmentById(id, AuthorizedAttachment.class);
        if (attachment != null && attachment.getState().equals(ContentState.UPLOADABLE)) {
            attachment.setState(ContentState.UPLOADED);
            _attachmentRepository.save(attachment);
            return AuthorizedAttachmentMapper.toDto(attachment, true);
        } else {
            return null;
        }
    }


//    TODO : MERGING OF PENDING AND AUTHORIZED
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
//            ReportDto result = _reportService.addReport(reportDto);
//            if (result != null) {
            Report report = ReportMapper.toEntity(reportDto, true);
            report.setAttachment(attachment);
            attachment.getReports().add(report);
            _attachmentRepository.save(attachment);
//            _reportService.saveInRepository(report);
            return ReportMapper.toDto(report, true);
        }
        return null;
    }
//    private Attachment updateAttachment(AttachmentDto attachmentDto, UserPermission permission) {
//        var builder = _attachmentBuilderFactory.createAttachmentBuilder(permission);
//        builder.setName(attachmentDto.getName());
//        builder.setDescription(attachmentDto.getDescription());
//        builder.setAuthor(attachmentDto.getAuthor());
//        builder.setCreationDate(attachmentDto.getCreationDate());
//        builder.setExpiryDate(attachmentDto.getExpiryDate());
//        builder.setState(attachmentDto.getState());
//        if (attachmentDto.getPoi() != null) {
//            builder.setPoi(GenericPOIMapper.toEntity(attachmentDto.getPoi(), true));
//        }
//        if (attachmentDto.getItinerary() != null) {
//            builder.setItinerary(GenericItineraryMapper.toEntity(attachmentDto.getItinerary(), true));
//        }
//        var attachment = builder.build();
//        attachment.setId(attachmentDto.getId());
//        return attachment;
//    }
}
