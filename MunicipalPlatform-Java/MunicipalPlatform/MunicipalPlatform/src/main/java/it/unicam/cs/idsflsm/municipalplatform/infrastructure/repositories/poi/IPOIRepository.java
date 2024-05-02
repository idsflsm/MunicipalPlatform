package it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.poi;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.POI;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IPOIRepository extends JpaRepository<POI, UUID> {
}
