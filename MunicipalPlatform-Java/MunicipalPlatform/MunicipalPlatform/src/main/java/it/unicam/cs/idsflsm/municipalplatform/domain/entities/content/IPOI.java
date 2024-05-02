package it.unicam.cs.idsflsm.municipalplatform.domain.entities.content;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedTourist;

import java.util.List;

public interface IPOI extends IContent {
    List<Itinerary> getPoiItineraries();
    List<AuthenticatedTourist> getTourists();
    List<Attachment> getAttachments();
}
