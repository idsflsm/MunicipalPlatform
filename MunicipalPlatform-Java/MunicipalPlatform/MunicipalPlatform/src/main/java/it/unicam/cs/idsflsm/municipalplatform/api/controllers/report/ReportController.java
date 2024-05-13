package it.unicam.cs.idsflsm.municipalplatform.api.controllers.report;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.attachment.IAttachmentService;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.report.IReportService;
import it.unicam.cs.idsflsm.municipalplatform.application.criterias.report.ReportCriteria;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.report.ReportDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.report.AddReportRequest;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.report.UpdateReportRequest;
import it.unicam.cs.idsflsm.municipalplatform.application.services.report.ReportService;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.report.Report;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

@RestController
@Validated
@RequestMapping("/api/reports")
@AllArgsConstructor(onConstructor_ = @Autowired)
public class ReportController {
    private final IReportService _reportService;
    private final IAttachmentService _attachmentService;
    @GetMapping
    public ResponseEntity<?> getAllReports
            (@RequestParam(required = false) String motivation) {
        Predicate<Report> criterias = ReportCriteria.hasMotivation(motivation);
        List<ReportDto> reportDtos = _reportService.getAllReports(Optional.of(criterias));
        if (!reportDtos.isEmpty()) {
            return new ResponseEntity<>(reportDtos, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getReport(@PathVariable("id") UUID id) {
        ReportDto reportDto = _reportService.getReportById(id);
        if (reportDto != null) {
            return new ResponseEntity<>(reportDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
    @PostMapping("/{idAttachment}")
    public ResponseEntity<?> addReport(@PathVariable("idAttachment") UUID idAttachment, AddReportRequest request) {
        Optional<Predicate<Attachment>> predicate = Optional.of(attachment -> attachment.getId().equals(idAttachment));
        ReportDto reportDto = new ReportDto();
        reportDto.setMotivation(request.getMotivation());
        boolean result = _attachmentService.addReport(reportDto, predicate);
        if (result) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReport(@PathVariable("id") UUID id) {
        boolean result = _reportService.deleteReportById(id);
        if (result) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateReport(@PathVariable("id") UUID id, @Valid @RequestBody UpdateReportRequest request) {
        ReportDto existingReport = _reportService.getReportById(id);
        if (existingReport != null) {
            existingReport.setMotivation(request.getMotivation());
            boolean result = _reportService.updateReport(existingReport, Optional.empty());
            if (result) {
                return new ResponseEntity<>(existingReport, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
