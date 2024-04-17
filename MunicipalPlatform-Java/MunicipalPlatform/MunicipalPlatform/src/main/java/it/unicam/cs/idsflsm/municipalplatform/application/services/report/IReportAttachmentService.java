package it.unicam.cs.idsflsm.municipalplatform.application.services.report;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Content;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.POI;

import java.util.List;
import java.util.UUID;

public interface IReportAttachmentService {
    List<Content> getAllPOIsandItineraries();
    List<Attachment> getAllPOIAttachments(POI poi);
    List<Attachment> getAllItineraryAttachments(Itinerary itinerary);
    POI selectPOI(UUID id);
    Itinerary selectItinerary(UUID id);
    void addReport(String motivation);
    Attachment selectAttachment(UUID id);
}
