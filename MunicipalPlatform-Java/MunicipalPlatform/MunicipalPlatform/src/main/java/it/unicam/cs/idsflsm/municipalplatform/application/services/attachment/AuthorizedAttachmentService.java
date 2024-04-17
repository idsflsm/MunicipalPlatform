package it.unicam.cs.idsflsm.municipalplatform.application.services.attachment;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Content;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.POI;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.contexts.attachment.ReadyStateAttachments;

import java.util.List;
import java.util.UUID;

public class AuthorizedAttachmentService implements IAuthorizedAttachmentService {
    private final ReadyStateAttachments _readyStateAttachments;
    public AuthorizedAttachmentService(ReadyStateAttachments readyStateAttachments) {
        _readyStateAttachments = readyStateAttachments;
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
}
