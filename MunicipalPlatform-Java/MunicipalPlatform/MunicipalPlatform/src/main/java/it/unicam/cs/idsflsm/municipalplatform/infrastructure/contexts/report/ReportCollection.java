package it.unicam.cs.idsflsm.municipalplatform.infrastructure.contexts.report;

import it.unicam.cs.idsflsm.municipalplatform.domain.entities.report.Report;

import java.util.List;

public class ReportCollection {
    private final List<Report> reports;
    public ReportCollection(List<Report> reports) {
        this.reports = reports;
    }
    public void addToList(Report report) {
        reports.add(report);
    }
}
