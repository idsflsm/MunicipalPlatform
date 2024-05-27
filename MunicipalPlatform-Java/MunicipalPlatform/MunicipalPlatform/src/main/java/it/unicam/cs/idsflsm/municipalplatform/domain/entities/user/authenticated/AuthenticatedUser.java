package it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contest;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "authenticated_user")
public abstract class AuthenticatedUser implements IAuthenticatedUser {
    @Id
    // @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "password", nullable = false, unique = false)
    private String password;
    @Column(name = "name", nullable = false, unique = false)
    private String name;
    @Column(name = "surname", nullable = false, unique = false)
    private String surname;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "user_poi",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "poi_id")
    )
    private List<POI> pois = new ArrayList<POI>();
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "user_itinerary",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "itinerary_id")
    )
    private List<Itinerary> itineraries = new ArrayList<Itinerary>();
    @ManyToMany(mappedBy = "participatingUsers", fetch = FetchType.EAGER)
    private List<Contest> participatedContests = new ArrayList<Contest>();
    public AuthenticatedUser() {
    }
    public AuthenticatedUser
            (UUID id, String username, String password, String name, String surname, List<POI> pois, List<Itinerary> itineraries, UserRole role) {
        
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.pois = pois;
        this.itineraries = itineraries;
        this.role = role;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        AuthenticatedUser other = (AuthenticatedUser) obj;
        return other.getId().equals(this.getId());
    }
}
