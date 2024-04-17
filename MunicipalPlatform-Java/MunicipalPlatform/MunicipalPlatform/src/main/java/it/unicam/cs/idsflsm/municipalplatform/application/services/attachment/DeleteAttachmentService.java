package it.unicam.cs.idsflsm.municipalplatform.application.services.attachment;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Content;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentStatus;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.contexts.attachment.DeletingStateAttachments;

import java.util.List;
import java.util.UUID;

public class DeleteAttachmentService implements IDeleteAttachmentService {
    private final DeletingStateAttachments _deletingStateAttachments;
    public DeleteAttachmentService(DeletingStateAttachments deletingStateAttachments) {
        _deletingStateAttachments = deletingStateAttachments;
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
    public Attachment selectAttachment(UUID id) {
        return null;
    }
    @Override
    public void setState(ContentStatus state) {

    }
}
