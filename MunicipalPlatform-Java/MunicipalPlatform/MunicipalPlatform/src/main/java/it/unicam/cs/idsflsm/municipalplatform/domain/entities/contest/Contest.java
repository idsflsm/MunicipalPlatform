package it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedTourist;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Entity
@Getter
@Setter
@Table(name = "contest")
public class Contest implements IContest {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "author", nullable = false, unique = false)
    private String author;
    @Column(name = "description", nullable = true, unique = false)
    private String description;
    @Embedded
    @AttributeOverride(name = "day", column = @Column(name = "contest_creation_date_day"))
    @AttributeOverride(name = "month", column = @Column(name = "contest_creation_date_month"))
    @AttributeOverride(name = "year", column = @Column(name = "contest_creation_date_year"))
    private Date creationDate;
    @Embedded
    @AttributeOverride(name = "day", column = @Column(name = "contest_expiry_date_day"))
    @AttributeOverride(name = "month", column = @Column(name = "contest_expiry_date_month"))
    @AttributeOverride(name = "year", column = @Column(name = "contest_expiry_date_year"))
    private Date expiryDate;
    @Column(name = "has_winner", nullable = false, unique = false)
    private boolean hasWinner = false;
    @OneToMany(mappedBy = "contest", cascade = CascadeType.ALL)
    private List<Contribution> contributions;
    @ManyToMany
    @JoinTable(
            name = "contest_participants",
            joinColumns = @JoinColumn(name = "contest_id"),
            inverseJoinColumns = @JoinColumn(name = "tourist_id")
    )
    private List<AuthenticatedTourist> participatingTourists = new ArrayList<>();
    public Contest() {
    }
    public Contest(UUID id, String name, String author, String description, Date creationDate, Date expiryDate, boolean hasWinner, List<Contribution> contributions, List<AuthenticatedTourist> participatingTourists) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.description = description;
        this.creationDate = creationDate;
        this.expiryDate = expiryDate;
        this.hasWinner = hasWinner;
        this.contributions = contributions;
        this.participatingTourists = participatingTourists;
    }
}
