package it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest;
/**
 * Represents a contest of contribution. Provides method to detach contest entity
 * from relationships with other entities, in order to manage entity persistence
 */
public interface IContest {
    /**
     * Method for detachment of contest entity
     * from relationships with other entities
     */
    void detachFromEntities();
}
