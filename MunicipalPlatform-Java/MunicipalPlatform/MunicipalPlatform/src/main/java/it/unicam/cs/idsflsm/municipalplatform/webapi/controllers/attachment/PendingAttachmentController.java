package it.unicam.cs.idsflsm.municipalplatform.webapi.controllers.attachment;

import it.unicam.cs.idsflsm.municipalplatform.application.services.attachment.IPendingAttachmentService;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Content;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.POI;

import java.util.List;
import java.util.UUID;

public class PendingAttachmentController {
    private final IPendingAttachmentService _pendingAttachmentService;
    public PendingAttachmentController(IPendingAttachmentService pendingAttachmentService) {
        _pendingAttachmentService = pendingAttachmentService;
    }
    public List<Content> getAllPOIsandItineraries() {

    }
    public Content select(UUID id) {

    }
    public List<Attachment> getAllPOIAttachments(POI poi) {

    }
    public List<Attachment> getAllItineraryAttachments(Itinerary itinerary) {

    }
    public boolean confirm() {

    }
    public void addAttachment(String motivation) {

    }
    public Attachment selectAttachment(UUID id) {

    }
    public boolean validate(Attachment attachment) {

    }
}
