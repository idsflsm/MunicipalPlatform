package it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.report;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.report.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IReportRepository extends JpaRepository<Report, UUID> {
}
