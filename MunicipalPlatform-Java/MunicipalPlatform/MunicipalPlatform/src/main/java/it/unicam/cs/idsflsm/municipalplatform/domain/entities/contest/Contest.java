package it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedTourist;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;
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
    // @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
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
    @OneToMany(mappedBy = "contest", cascade = CascadeType.ALL, orphanRemoval = true,
            fetch = FetchType.EAGER)
    private List<Contribution> contributions = new ArrayList<Contribution>();
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinTable(
            name = "contest_participants",
            joinColumns = @JoinColumn(name = "contest_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<AuthenticatedUser> participatingUsers = new ArrayList<AuthenticatedUser>();
    public Contest() {
    }
    public Contest(UUID id, String name, String author, String description, Date creationDate, Date expiryDate, boolean hasWinner, List<Contribution> contributions, List<AuthenticatedUser> participatingUsers) {
        
        this.name = name;
        this.author = author;
        this.description = description;
        this.creationDate = creationDate;
        this.expiryDate = expiryDate;
        this.hasWinner = hasWinner;
        this.contributions = contributions;
        this.participatingUsers = participatingUsers;
    }
}
