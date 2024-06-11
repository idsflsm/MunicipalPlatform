package it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.attachment;
import it.unicam.cs.idsflsm.municipalplatform.application.criterias.attachment.AttachmentCriteria;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentState;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.GenericRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
/**
 * Interface that represents the repository for Attachment entity
 */
@Repository
public interface IAttachmentRepository extends GenericRepository<Attachment, UUID> {
    default List<Attachment> findByExpiryDate(LocalDate expiryDate) {
        return findByPredicate(AttachmentCriteria.hasExpiryDate(Date.toDate(expiryDate)));
    }
    default List<Attachment> findByRemovableState() {
        return findByPredicate(AttachmentCriteria.hasState(ContentState.REMOVABLE));
    }
}
