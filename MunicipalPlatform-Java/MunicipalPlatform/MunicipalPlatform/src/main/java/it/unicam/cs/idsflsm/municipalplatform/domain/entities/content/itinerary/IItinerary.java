package it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.IContent;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedTourist;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedUser;

import java.util.List;

public interface IItinerary extends IContent {
    List<POI> getItineraryPois();
    List<AuthenticatedUser> getUsers();
    List<Attachment> getAttachments();
}
