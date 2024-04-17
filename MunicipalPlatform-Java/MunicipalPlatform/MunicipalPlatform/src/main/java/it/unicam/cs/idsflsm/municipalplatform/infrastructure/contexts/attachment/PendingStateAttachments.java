package it.unicam.cs.idsflsm.municipalplatform.infrastructure.contexts.attachment;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Attachment;

import java.util.List;

public class PendingStateAttachments {
    private final List<Attachment> attachments;
    public PendingStateAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }
    public void addToList(Attachment attachment) {
        attachments.add(attachment);
    }
}
