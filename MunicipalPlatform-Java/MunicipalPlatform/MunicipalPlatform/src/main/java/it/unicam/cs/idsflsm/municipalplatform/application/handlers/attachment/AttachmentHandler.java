package it.unicam.cs.idsflsm.municipalplatform.application.handlers.attachment;

import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.handlers.attachment.IAttachmentHandler;
import it.unicam.cs.idsflsm.municipalplatform.application.factories.attachment.AttachmentBuilderFactory;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.itinerary.GenericItineraryMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.poi.GenericPOIMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.report.ReportMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserPermission;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Handler class for Attachment modification
 */
@Component
@AllArgsConstructor(onConstructor_ = @Autowired)
public class AttachmentHandler implements IAttachmentHandler {
    /**
     * The factory responsible for creating Attachment builders
     */
    private final AttachmentBuilderFactory _attachmentBuilderFactory;
    @Override
    public Attachment modifyAttachment(AttachmentDto attachmentDto, UserPermission permission) {
        var builder = _attachmentBuilderFactory.createAttachmentBuilder(permission);
        builder.setName(attachmentDto.getName());
        builder.setDescription(attachmentDto.getDescription());
        builder.setAuthor(attachmentDto.getAuthor());
        builder.setCreationDate(attachmentDto.getCreationDate());
        builder.setExpiryDate(attachmentDto.getExpiryDate());
        builder.setState(attachmentDto.getState());
        if (attachmentDto.getPoi() != null) {
            builder.setPoi(GenericPOIMapper.toEntity(attachmentDto.getPoi(), true));
        }
        if (attachmentDto.getItinerary() != null) {
            builder.setItinerary(GenericItineraryMapper.toEntity(attachmentDto.getItinerary(), true));
        }
        builder.setReports(ReportMapper.toEntity(attachmentDto.getReports(), true));
        var attachment = builder.build();
        attachment.setId(attachmentDto.getId());
        return attachment;
    }
}
