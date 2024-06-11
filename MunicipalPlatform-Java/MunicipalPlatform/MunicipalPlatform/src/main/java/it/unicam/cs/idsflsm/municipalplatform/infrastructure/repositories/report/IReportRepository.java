package it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.report;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.report.Report;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.GenericRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
/**
 * Interface that represents the repository for Report entity
 */
@Repository
public interface IReportRepository extends GenericRepository<Report, UUID> {
}
