package it.unicam.cs.idsflsm.municipalplatform.domain.entities.content;

import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.ContentStatus;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;

import java.util.UUID;

public class PendingAttachment extends Attachment {

    public PendingAttachment
        (UUID id, String name,
         Date expiryDate, ContentStatus state) {

    }

    @Override
    public UUID getId() {
        return null;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public Date getExpiryDate() {
        return null;
    }

    @Override
    public ContentStatus getState() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public void setExpiryDate(Date expiryDate) {

    }

    @Override
    public void setState(ContentStatus state) {

    }

}
