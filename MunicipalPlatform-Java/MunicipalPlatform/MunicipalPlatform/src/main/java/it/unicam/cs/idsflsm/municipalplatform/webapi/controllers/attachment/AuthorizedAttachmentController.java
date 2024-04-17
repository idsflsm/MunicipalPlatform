package it.unicam.cs.idsflsm.municipalplatform.webapi.controllers.attachment;

import it.unicam.cs.idsflsm.municipalplatform.application.services.attachment.IAuthorizedAttachmentService;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.*;

import java.util.List;
import java.util.UUID;

public class AuthorizedAttachmentController {
    private final IAuthorizedAttachmentService _authorizedAttachmentService;
    public AuthorizedAttachmentController(IAuthorizedAttachmentService authorizedAttachmentService) {
        _authorizedAttachmentService = authorizedAttachmentService;
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
}
