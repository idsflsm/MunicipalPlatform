package it.unicam.cs.idsflsm.municipalplatform.infrastructure.contexts.attachment;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.POI;

import java.util.List;

public class DeletingStateAttachments {
    private final List<Attachment> attachments;
    public DeletingStateAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }
    public void addToList(Attachment attachment) {
        attachments.add(attachment);
    }
}
