package it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.report;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.report.ReportDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.report.Report;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
public interface IReportService {
    void saveInRepository(Report report);
    List<ReportDto> getAllReports(Optional<Predicate<Report>> predicate);
    ReportDto getReportById(UUID id);
//    ReportDto addReport(ReportDto reportDto);
//    ReportDto deleteReportById(UUID id);
//    boolean deleteReport(ReportDto report, Optional<Predicate<Report>> predicate);
    ReportDto validateReport(UUID id, boolean validate);
    Attachment getReportAttachment(UUID idReport);
}
