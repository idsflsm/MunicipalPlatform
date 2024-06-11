package it.unicam.cs.idsflsm.municipalplatform.application.builders.attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.PendingAttachment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
/**
 * Specific builder class for the building of PendingAttachment instances
 */
@Component
@Getter
@Setter
@NoArgsConstructor
public class PendingAttachmentBuilder extends AttachmentBuilder {
    @Override
    public Attachment build() {
        return new PendingAttachment(
                this.getName(),
                this.getDescription(),
                this.getAuthor(),
                this.getCreationDate(),
                this.getExpiryDate(),
                this.getState(),
                this.getPoi(),
                this.getItinerary(),
                this.getReports()
        );
    }
}
