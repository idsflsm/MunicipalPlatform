package it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.IContent;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedTourist;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;

import java.util.List;

public interface IPOI extends IContent {
    List<Itinerary> getPoiItineraries();
    List<AuthenticatedUser> getUsers();
    List<Attachment> getAttachments();

    void detachFromEntities();
//    void checkItinerariesSize();
}
