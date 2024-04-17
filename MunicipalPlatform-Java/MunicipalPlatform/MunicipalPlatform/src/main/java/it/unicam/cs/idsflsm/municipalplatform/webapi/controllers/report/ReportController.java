package it.unicam.cs.idsflsm.municipalplatform.webapi.controllers.report;

import it.unicam.cs.idsflsm.municipalplatform.application.services.report.IReportAttachmentService;
import it.unicam.cs.idsflsm.municipalplatform.application.services.report.IResponseReportService;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Content;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.report.Report;

import java.util.List;
import java.util.UUID;

public class ReportController {
    private final IReportAttachmentService _reportAttachmentService;
    private final IResponseReportService _responseReportService;
    public ReportController(IReportAttachmentService reportAttachmentService, IResponseReportService responseReportService) {
        _reportAttachmentService = reportAttachmentService;
        _responseReportService = responseReportService;
    }
    public List<Content> getAllPOIsandItineraries() {

    }
    public Content select(UUID id) {

    }
    public List<Attachment> getAllPOIAttachments(POI poi) {

    }
    public List<Attachment> getAllItineraryAttachments(Itinerary itinerary) {

    }
    public Attachment selectAttachment(UUID id) {

    }
    public boolean report(Attachment attachment) {

    }
    public void addReport(String motivation) {

    }
    public List<Report> getAllReports() {

    }
    public Report selectReport(UUID id) {

    }
    public boolean ignore(Report report) {

    }
    public boolean confirm(Report report) {

    }
}
