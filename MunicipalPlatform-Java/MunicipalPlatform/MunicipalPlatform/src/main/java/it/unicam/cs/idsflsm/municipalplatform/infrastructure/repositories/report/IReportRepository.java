package it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.report;

import it.unicam.cs.idsflsm.municipalplatform.application.criterias.report.ReportCriteria;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.report.Report;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.GenericRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
@Repository
public interface IReportRepository extends GenericRepository<Report, UUID> {
    default List<Report> findByMotivation(String motivation) {
        return findByPredicate(ReportCriteria.hasMotivation(motivation));
    }
}
