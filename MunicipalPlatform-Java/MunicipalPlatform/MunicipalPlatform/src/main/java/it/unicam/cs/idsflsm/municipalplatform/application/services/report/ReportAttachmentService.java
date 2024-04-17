package it.unicam.cs.idsflsm.municipalplatform.application.services.report;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Content;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.POI;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.contexts.report.ReportCollection;

import java.util.List;
import java.util.UUID;

public class ReportAttachmentService implements IReportAttachmentService {
    private final ReportCollection _reportCollection;
    public ReportAttachmentService(ReportCollection reportCollection) {
        _reportCollection = reportCollection;
    }
    @Override
    public List<Content> getAllPOIsandItineraries() {
        return List.of();
    }
    @Override
    public List<Attachment> getAllPOIAttachments(POI poi) {
        return List.of();
    }
    @Override
    public List<Attachment> getAllItineraryAttachments(Itinerary itinerary) {
        return List.of();
    }
    @Override
    public POI selectPOI(UUID id) {
        return null;
    }
    @Override
    public Itinerary selectItinerary(UUID id) {
        return null;
    }
    @Override
    public void addReport(String motivation) {

    }
    @Override
    public Attachment selectAttachment(UUID id) {
        return null;
    }
}
