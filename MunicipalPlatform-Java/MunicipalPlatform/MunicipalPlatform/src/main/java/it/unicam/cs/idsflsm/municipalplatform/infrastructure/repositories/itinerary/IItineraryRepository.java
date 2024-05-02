package it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.itinerary;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IItineraryRepository extends JpaRepository<Itinerary, UUID> {
}
