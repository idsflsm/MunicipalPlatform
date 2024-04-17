package it.unicam.cs.idsflsm.municipalplatform.infrastructure.contexts.attachment;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Attachment;

import java.util.List;

public class ReadyStateAttachments {
    private final List<Attachment> attachments;
    public ReadyStateAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }
    public void addToList(Attachment attachment) {
        attachments.add(attachment);
    }
}
