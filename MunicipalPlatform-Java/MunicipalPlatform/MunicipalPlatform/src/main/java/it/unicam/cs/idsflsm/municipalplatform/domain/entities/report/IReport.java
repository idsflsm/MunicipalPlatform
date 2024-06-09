package it.unicam.cs.idsflsm.municipalplatform.domain.entities.report;

import java.util.UUID;
/**
 * Represents a report. Provides method to detach report entity from relationships
 * with other entities, in order to manage entity persistence
 */
public interface IReport {
    /**
     * Method for detachment of report entity
     * from relationships with other entities
     */
    void detachFromEntities();
}
