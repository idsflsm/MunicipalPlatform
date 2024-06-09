package it.unicam.cs.idsflsm.municipalplatform.domain.entities.report;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Represents a report on an attachment already loaded on the platform.
 * It contains the motivation of the report and its associated attachment
 */
@Entity
@Getter
@Setter
@Table(name = "report")
public class Report implements IReport {
    /**
     * The unique identifier of the report
     */
    @Id
    // @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;
    /**
     * The motivation of the report
     */
    @Column(name = "motivation", nullable = false, unique = false)
    private String motivation;
    /**
     * The attachment associated to the report
     */
    @ManyToOne(fetch = FetchType.EAGER/*, cascade = CascadeType.REMOVE*/)
    @JoinColumn(name = "attachment", nullable = true)
    private Attachment attachment;
    public Report() {
    }
    public Report(String motivation, Attachment attachment) {
        this.motivation = motivation;
        this.attachment = attachment;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Report other = (Report) obj;
        return other.getId().equals(this.getId())
                || other.getMotivation().equalsIgnoreCase(this.getMotivation());
    }
    @Override
    public void detachFromEntities() {
        this.attachment.getReports().remove(this);
        this.attachment = null;
    }
}
