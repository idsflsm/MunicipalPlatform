package it.unicam.cs.idsflsm.municipalplatform.infrastructure.scheduling.attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.attachment.IAttachmentRepository;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.scheduling.GenericScheduledTask;
import org.springframework.stereotype.Component;
/**
 * Scheduled task class for managing deletion of Attachment entity
 */
@Component
public class AttachmentScheduledTask extends GenericScheduledTask<Attachment> {
    public AttachmentScheduledTask(IAttachmentRepository attachmentRepository) {
        super(attachmentRepository);
    }
    /**
     * Method that deletes attachments based on their expiry date
     */
    public void deleteAttachmentsByExpiryDate() {
        this.setMethodName("findByExpiryDate");
        this.deleteEntities();
    }
    /**
     * Method that deletes attachments based on their REMOVABLE state
     */
    public void deleteAttachmentsByRemovableState() {
        this.setMethodName("findByRemovableState");
        this.deleteEntities();
    }
}