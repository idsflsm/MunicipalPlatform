package it.unicam.cs.idsflsm.municipalplatform.domain.entities.report;

import java.util.UUID;

public interface IReport {
    UUID getId();
    String getMotivation();
    void setMotivation(String motivation);
}
