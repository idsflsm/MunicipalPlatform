package it.unicam.cs.idsflsm.municipalplatform.application.builders.attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.AuthorizedAttachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.report.Report;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
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
