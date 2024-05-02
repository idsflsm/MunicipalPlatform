package it.unicam.cs.idsflsm.municipalplatform.domain.entities.content;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedTourist;

import java.util.List;

public interface IItinerary extends IContent {
    List<POI> getItineraryPois();
    List<AuthenticatedTourist> getTourists();
    List<Attachment> getAttachments();
}
