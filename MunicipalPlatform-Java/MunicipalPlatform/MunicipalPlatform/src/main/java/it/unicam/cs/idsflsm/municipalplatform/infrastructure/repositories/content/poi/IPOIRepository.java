package it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.content.poi;

import it.unicam.cs.idsflsm.municipalplatform.application.criterias.content.itinerary.ItineraryCriteria;
import it.unicam.cs.idsflsm.municipalplatform.application.criterias.content.poi.POICriteria;
import it.unicam.cs.idsflsm.municipalplatform.application.criterias.contest.ContestCriteria;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contest;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.GenericRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
public interface IPOIRepository extends GenericRepository<POI, UUID> {
    default List<POI> findByExpiryDate(LocalDate expiryDate) {
        return findByPredicate(POICriteria.hasExpiryDate(Date.toDate(expiryDate)));
    }
    default List<POI> findByRemovableState() {
        return findByPredicate(POICriteria.hasState(ContentState.REMOVABLE));
    }
}
