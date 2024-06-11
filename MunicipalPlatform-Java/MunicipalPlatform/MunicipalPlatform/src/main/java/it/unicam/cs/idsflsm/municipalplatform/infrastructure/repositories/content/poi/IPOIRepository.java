package it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.content.poi;
import it.unicam.cs.idsflsm.municipalplatform.application.criterias.content.poi.POICriteria;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.GenericRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
/**
 * Interface that represents the repository for POI entity
 */
@Repository
public interface IPOIRepository extends GenericRepository<POI, UUID> {
    default List<POI> findByExpiryDate(LocalDate expiryDate) {
        return findByPredicate(POICriteria.hasExpiryDate(Date.toDate(expiryDate)));
    }
    default List<POI> findByRemovableState() {
        return findByPredicate(POICriteria.hasState(ContentState.REMOVABLE));
    }
}
