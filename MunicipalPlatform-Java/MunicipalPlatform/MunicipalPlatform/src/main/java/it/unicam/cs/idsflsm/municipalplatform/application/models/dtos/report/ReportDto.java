package it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.report;
import com.fasterxml.jackson.annotation.JsonBackReference;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportDto {
    private UUID id = UUID.randomUUID();
    private String motivation;
    @JsonBackReference
    private Attachment attachment;
}
