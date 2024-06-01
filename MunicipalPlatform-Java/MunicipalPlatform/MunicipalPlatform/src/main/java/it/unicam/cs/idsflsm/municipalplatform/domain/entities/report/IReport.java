package it.unicam.cs.idsflsm.municipalplatform.domain.entities.report;

import java.util.UUID;

public interface IReport {
    UUID getId();
    void setId(UUID id);
    String getMotivation();
    void setMotivation(String motivation);

    void detachFromEntities();
}
