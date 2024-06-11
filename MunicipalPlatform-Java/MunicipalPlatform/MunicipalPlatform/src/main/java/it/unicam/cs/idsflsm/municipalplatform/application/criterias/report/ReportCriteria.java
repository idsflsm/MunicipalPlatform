package it.unicam.cs.idsflsm.municipalplatform.application.criterias.report;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.report.Report;

import java.util.UUID;
import java.util.function.Predicate;
/**
 * Utility class providing various predicates for filtering Report objects based on different criteria.
 * In general, predicates on entity fields are optional filters
 */
public class ReportCriteria {
    public static Predicate<Report> hasId(UUID id) {
        if (id != null) {
            return report -> report.getId().equals(id);
        } else {
            return report -> true;
        }
    }
    public static Predicate<Report> hasMotivation(String motivation) {
        if (motivation != null && !motivation.isBlank()) {
            return report -> report.getMotivation().toLowerCase().contains(motivation.toLowerCase());
        } else {
            return report -> true;
        }
    }
    @SafeVarargs
    public static Predicate<Report> criteriaBuilder(Predicate<Report>... predicates) {
        Predicate<Report> result = report -> true;
        for (Predicate<Report> predicate : predicates) {
            result = result.and(predicate);
        }
        return result;
    }
}
