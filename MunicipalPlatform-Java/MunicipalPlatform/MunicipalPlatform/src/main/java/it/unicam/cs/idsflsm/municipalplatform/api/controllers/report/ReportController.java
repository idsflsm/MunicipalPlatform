package it.unicam.cs.idsflsm.municipalplatform.api.controllers.report;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.report.IReportService;
import it.unicam.cs.idsflsm.municipalplatform.application.services.report.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/reports")
@AllArgsConstructor(onConstructor_ = @Autowired)
public class ReportController {
    private final IReportService _reportService;
}
