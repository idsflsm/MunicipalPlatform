package it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.content.itinerary;

import it.unicam.cs.idsflsm.municipalplatform.application.criterias.content.itinerary.ItineraryCriteria;
import it.unicam.cs.idsflsm.municipalplatform.application.criterias.contest.ContestCriteria;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contest;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.GenericRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
@Repository
public interface IItineraryRepository extends GenericRepository<Itinerary, UUID> {
    default List<Itinerary> findByExpiryDate(LocalDate expiryDate) {
        return findByPredicate(ItineraryCriteria.hasExpiryDate(Date.toDate(expiryDate)));
    }
    default List<Itinerary> findByRemovableState() {
        return findByPredicate(ItineraryCriteria.hasState(ContentState.REMOVABLE));
    }
}
