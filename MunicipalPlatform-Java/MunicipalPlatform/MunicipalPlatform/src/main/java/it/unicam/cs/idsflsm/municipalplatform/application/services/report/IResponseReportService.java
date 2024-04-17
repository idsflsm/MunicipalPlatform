package it.unicam.cs.idsflsm.municipalplatform.application.services.report;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.report.Report;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentStatus;

import java.util.List;
import java.util.UUID;

public interface IResponseReportService {
    List<Report> getAllReports();
    Report selectReport(UUID id);
    void setState(ContentStatus state);
}
