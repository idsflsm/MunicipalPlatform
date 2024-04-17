package it.unicam.cs.idsflsm.municipalplatform.application.services.report;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.report.Report;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentStatus;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.contexts.attachment.DeletingStateAttachments;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.contexts.report.ReportCollection;

import java.util.List;
import java.util.UUID;

public class ResponseReportService implements IResponseReportService {
    private final ReportCollection _reportCollection;
    private final DeletingStateAttachments _deletingStateAttachments;
    public ResponseReportService(ReportCollection reportCollection) {
        _reportCollection = reportCollection;
        _deletingStateAttachments = new DeletingStateAttachments();
    }
    @Override
    public List<Report> getAllReports() {
        return List.of();
    }
    @Override
    public Report selectReport(UUID id) {
        return null;
    }
    @Override
    public void setState(ContentStatus state) {

    }
}
