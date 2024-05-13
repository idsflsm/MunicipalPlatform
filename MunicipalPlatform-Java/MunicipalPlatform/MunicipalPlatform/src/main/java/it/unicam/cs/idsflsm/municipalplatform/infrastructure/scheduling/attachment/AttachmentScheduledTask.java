package it.unicam.cs.idsflsm.municipalplatform.infrastructure.scheduling.attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.GenericRepository;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.attachment.IAttachmentRepository;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.scheduling.GenericScheduledTask;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
public class AttachmentScheduledTask extends GenericScheduledTask<Attachment> {
    public AttachmentScheduledTask(IAttachmentRepository attachmentRepository) {
        super(attachmentRepository);
    }
    public void deleteAttachmentsByExpiryDate() {
        this.setMethodName("findByExpiryDate");
        this.deleteEntities();
    }
    public void deleteAttachmentsByRemovableState() {
        this.setMethodName("findByRemovableState");
        this.deleteEntities();
    }
}
