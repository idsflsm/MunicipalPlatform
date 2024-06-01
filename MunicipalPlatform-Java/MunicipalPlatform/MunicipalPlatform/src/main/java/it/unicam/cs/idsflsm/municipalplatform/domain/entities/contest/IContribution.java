package it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Content;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.poi.POI;
public interface IContribution {
    Content getContent();
    void setContent(Content content);
    void setNotNullPoi(POI poi);
    void setNotNullItinerary(Itinerary itinerary);

    void detachFromEntities(boolean contributionValidation);
}
