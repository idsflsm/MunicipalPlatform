package it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.attachment;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IAttachmentRepository extends JpaRepository<Attachment, UUID> {
}
