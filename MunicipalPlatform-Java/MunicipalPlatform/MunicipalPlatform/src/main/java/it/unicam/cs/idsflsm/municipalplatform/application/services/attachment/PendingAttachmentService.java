package it.unicam.cs.idsflsm.municipalplatform.application.services.attachment;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Content;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentStatus;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.contexts.attachment.DeletingStateAttachments;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.contexts.attachment.PendingStateAttachments;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.contexts.attachment.ReadyStateAttachments;

import java.util.List;
import java.util.UUID;

public class PendingAttachmentService implements IPendingAttachmentService {
    private final PendingStateAttachments _pendingStateAttachments;
    private final ReadyStateAttachments _readyStateAttachments;
    private final DeletingStateAttachments _deletingStateAttachments;
    public PendingAttachmentService(PendingStateAttachments pendingStateAttachments, ReadyStateAttachments readyStateAttachments, DeletingStateAttachments deletingStateAttachments) {
        this._pendingStateAttachments = pendingStateAttachments;
        this._readyStateAttachments = readyStateAttachments;
        this._deletingStateAttachments = deletingStateAttachments;
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
    public void addAttachment(String motivation) {

    }
    @Override
    public Attachment selectAttachment(UUID id) {
        return null;
    }
    @Override
    public void setState(ContentStatus state) {

    }
}
