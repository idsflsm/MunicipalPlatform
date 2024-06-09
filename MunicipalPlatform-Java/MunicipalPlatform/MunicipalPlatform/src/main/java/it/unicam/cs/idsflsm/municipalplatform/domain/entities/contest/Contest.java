package it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedTourist;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a contest of contribution. It contains general information about the contest,
 * including the list of participants and their contributions
 */
@Entity
@Getter
@Setter
@Table(name = "contest")
public class Contest implements IContest {
    /**
     * The unique identifier of the contest
     */
    @Id
    // @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;
    /**
     * The name of the contest
     */
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    /**
     * The author of the contest
     */
    @Column(name = "author", nullable = false, unique = false)
    private String author;
    /**
     * The description of the contest
     */
    @Column(name = "description", nullable = true, unique = false)
    private String description;
    /**
     * The creation date of the contest
     */
    @Embedded
    @AttributeOverride(name = "day", column = @Column(name = "contest_creation_date_day"))
    @AttributeOverride(name = "month", column = @Column(name = "contest_creation_date_month"))
    @AttributeOverride(name = "year", column = @Column(name = "contest_creation_date_year"))
    private Date creationDate;
    /**
     * The expiry date of the contest
     */
    @Embedded
    @AttributeOverride(name = "day", column = @Column(name = "contest_expiry_date_day"))
    @AttributeOverride(name = "month", column = @Column(name = "contest_expiry_date_month"))
    @AttributeOverride(name = "year", column = @Column(name = "contest_expiry_date_year"))
    private Date expiryDate;
    /**
     * The presence of a winner of the contest
     */
    @Column(name = "has_winner", nullable = false, unique = false)
    private boolean hasWinner = false;
    /**
     * The list of contributions of the contest
     */
    @OneToMany(mappedBy = "contest", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}, /* orphanRemoval = true, */
            fetch = FetchType.EAGER)
    private List<Contribution> contributions = new ArrayList<Contribution>();
    /**
     * The list of participants of the contest
     */
    @ManyToMany(fetch = FetchType.EAGER, cascade = {/* CascadeType.MERGE, */ CascadeType.PERSIST})
    @JoinTable(
            name = "contest_participants",
            joinColumns = @JoinColumn(name = "contest_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<AuthenticatedUser> participatingUsers = new ArrayList<AuthenticatedUser>();
    public Contest() {
    }
    public Contest(String name, String author, String description, Date creationDate, Date expiryDate, boolean hasWinner, List<Contribution> contributions, List<AuthenticatedUser> participatingUsers) {
        this.name = name;
        this.author = author;
        this.description = description;
        this.creationDate = creationDate;
        this.expiryDate = expiryDate;
        this.hasWinner = hasWinner;
        this.contributions = contributions;
        this.participatingUsers = participatingUsers;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Contest other = (Contest) obj;
        return other.getId().equals(this.getId())
                || other.getName().equalsIgnoreCase(this.getName());
    }
    @Override
    public void detachFromEntities() {
        this.participatingUsers.forEach(authenticatedUser -> authenticatedUser.getParticipatedContests().remove(this));
        setParticipatingUsers(new ArrayList<AuthenticatedUser>());
    }
}
