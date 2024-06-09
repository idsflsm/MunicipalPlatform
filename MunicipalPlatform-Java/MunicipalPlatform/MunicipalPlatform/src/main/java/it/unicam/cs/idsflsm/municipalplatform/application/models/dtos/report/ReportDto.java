package it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.report;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.attachment.AttachmentDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.report.Report;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;
/**
 * Represents a DTO related to the entity Report.
 * It contains all fields with simple types
 * and the DTOs of entity fields
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportDto {
    private UUID id = UUID.randomUUID();
    private String motivation;
    @JsonIgnore
    @JsonBackReference
    private AttachmentDto attachment;
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        ReportDto other = (ReportDto) obj;
        return other.getId().equals(this.getId())
                || other.getMotivation().equalsIgnoreCase(this.getMotivation());
    }
}
