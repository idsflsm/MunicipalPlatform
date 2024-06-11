package it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.Content;
/**
 * Represents a contribution for a contest. Provides methods to detach contest entity
 * from relationships with other entities, in order to manage entity persistence,
 * and to manipulate the corresponding created content
 */
public interface IContribution {
    /**
     * Method to get the corresponding created content, that can be
     * either a POI or an itinerary
     *
     * @return the corresponding content; an instance of POI if
     * the content is a POI, an instance of Itinerary otherwise
     */
    Content getContent();
    /**
     * Method to set the corresponding created content, that can be
     * either a POI or an Itinerary
     *
     * @param content the corresponding content, an instance either
     * of POI or of Itinerary
     */
    void setContent(Content content);
    /**
     * Method for detachment of contribution entity
     * from relationships with other entities, with a boolean
     * flag that specifies if contribution validation
     * is being performed
     */
    void detachFromEntities(boolean contributionValidation);
}
