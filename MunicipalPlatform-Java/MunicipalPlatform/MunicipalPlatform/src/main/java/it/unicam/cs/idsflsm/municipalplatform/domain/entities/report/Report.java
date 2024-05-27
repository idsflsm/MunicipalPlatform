package it.unicam.cs.idsflsm.municipalplatform.domain.entities.report;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "report")
public class Report implements IReport {
    @Id
    // @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;
    @Column(name = "motivation", nullable = false, unique = false)
    private String motivation;
    @ManyToOne(fetch = FetchType.EAGER/*, cascade = CascadeType.REMOVE*/)
    @JoinColumn(name = "attachment", nullable = true)
    private Attachment attachment;
    public Report() {
    }
    public Report(UUID id, String motivation, Attachment attachment) {
        
        this.motivation = motivation;
        this.attachment = attachment;
    }
}
