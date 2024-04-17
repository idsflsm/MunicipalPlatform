package it.unicam.cs.idsflsm.municipalplatform.domain.entities.report;

import java.util.UUID;

public class Report implements IReport {

    private final UUID id = UUID.randomUUID();
    private final String motivation;

    public Report(String motivation) {
        this.motivation = motivation;
    }

    @Override
    public UUID getId() {
        return null;
    }

    @Override
    public String getMotivation() {
        return "";
    }

    @Override
    public void setMotivation(String motivation) {

    }
}
