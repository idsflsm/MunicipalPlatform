package it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.report;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.report.ReportDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.report.Report;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
public interface IReportService {
    List<ReportDto> getAllReports(Optional<Predicate<Report>> predicate);
    ReportDto getReportById(UUID id);
    boolean addReport(ReportDto reportDto);
    boolean deleteReportById(UUID id);
    boolean deleteReport(ReportDto report, Optional<Predicate<Report>> predicate);
    boolean updateReport(ReportDto reportDto, Optional<Predicate<Report>> predicate);
}
