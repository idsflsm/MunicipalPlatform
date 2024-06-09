package it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.report;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.report.ReportDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.report.Report;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
/**
 * Interface for Report service class. It provides methods to manipulate persistent
 * reports in the database
 */
public interface IReportService {
    void saveInRepository(Report report);

    /**
     * Method that retrieves a list of Report DTOs based on a given predicate
     * @param predicate an Optional Predicate<Report> that can be used to filter the reports
     * @return the list of Report DTOs. If the predicate is present, the list will only contain
     * reports that satisfy the predicate. If no reports satisfy the predicate or if the
     * repository contains no reports, an empty list will be returned
     */
    List<ReportDto> getReports(Optional<Predicate<Report>> predicate);

    /**
     * Method that retrieves a Report DTO by its unique identifier
     * @param id the UUID of the report to be retrieved
     * @return the Report DTO if a report with the given UUID exists,
     * null otherwise
     */
    ReportDto getReportById(UUID id);
//    ReportDto addReport(ReportDto reportDto);
//    ReportDto deleteReportById(UUID id);
//    boolean deleteReport(ReportDto report, Optional<Predicate<Report>> predicate);
    /**
     * Method that validates a report based on a given validation flag
     * @param id the UUID of the report to be validated
     * @param validate boolean flag indicating whether the report should be validated or not
     * @return the Report DTO if the entity exists,
     * null otherwise
     */
    ReportDto validateReport(UUID id, boolean validate);
//    Attachment getReportAttachment(UUID idReport);
}
