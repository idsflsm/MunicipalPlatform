package it.unicam.cs.idsflsm.municipalplatform.application.builders.attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.AuthorizedAttachment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
/**
 * Specific builder class for the building of AuthorizedAttachment instances
 */
@Component
@Getter
@Setter
@NoArgsConstructor
public class AuthorizedAttachmentBuilder extends AttachmentBuilder {
    @Override
    public Attachment build() {
        return new AuthorizedAttachment(
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
