package it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id = UUID.randomUUID();
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "password", nullable = false, unique = false)
    private String password;
    @Column(name = "name", nullable = false, unique = false)
    private String name;
    @Column(name = "surname", nullable = false, unique = false)
    private String surname;
    @ManyToMany
    @JoinTable(
            name = "tourist_poi",
            joinColumns = @JoinColumn(name = "tourist_id"),
            inverseJoinColumns = @JoinColumn(name = "poi_id")
    )
    private List<POI> pois = new ArrayList<>();
    @ManyToMany
    @JoinTable(
            name = "tourist_itinerary",
            joinColumns = @JoinColumn(name = "tourist_id"),
            inverseJoinColumns = @JoinColumn(name = "itinerary_id")
    )
    private List<Itinerary> itineraries = new ArrayList<>();
    public AuthenticatedUser() {
    }
    public AuthenticatedUser
            (UUID id, String username, String password, String name, String surname, List<POI> pois, List<Itinerary> itineraries) {
        this.id = (id != null) ? id : UUID.randomUUID();
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.pois = pois;
        this.itineraries = itineraries;
    }
}
