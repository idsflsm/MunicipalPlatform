package it.unicam.cs.idsflsm.municipalplatform.api.controllers.report;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.attachment.IAttachmentService;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.report.IReportService;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.user.IUserService;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.validators.ValidUUID;
import it.unicam.cs.idsflsm.municipalplatform.application.criterias.report.ReportCriteria;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.report.ReportMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.report.ReportDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.report.AddReportRequest;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.report.UpdateReportRequest;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.report.ValidateReportRequest;
import it.unicam.cs.idsflsm.municipalplatform.application.services.report.ReportService;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.report.Report;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserPermission;
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
    private final IUserService _userService;
    @GetMapping
    public ResponseEntity<?> getAllReports
            (@RequestParam UUID idUser,
             @RequestParam(required = false) String motivation) {
        if (_userService.appropriateUser(idUser, UserPermission.CURATOR_REPORT_READ)) {
            Predicate<Report> criterias = ReportCriteria.hasMotivation(motivation);
            List<ReportDto> reportDtos = _reportService.getAllReports(Optional.of(criterias));
            if (!reportDtos.isEmpty()) {
                return new ResponseEntity<>(reportDtos, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getReport(@PathVariable("id") UUID id, @RequestParam UUID idUser) {
        if (_userService.appropriateUser(idUser, UserPermission.CURATOR_REPORT_READ)) {
            ReportDto reportDto = _reportService.getReportById(id);
            if (reportDto != null) {
                return new ResponseEntity<>(reportDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteReport(@RequestParam UUID idUser, @PathVariable("id") UUID id) {
//        if (_userService.appropriateUser(idUser, UserPermission.CURATOR_REPORT_DELETE)) {
//            ReportDto result = _reportService.deleteReportById(id);
//            if (result != null) {
//                return new ResponseEntity<>(result, HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            }
//        } else {
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }
//    }
    @PutMapping("/{id}")
    public ResponseEntity<?> validateReport(@PathVariable("id") UUID id, @RequestBody ValidateReportRequest request) {
        if (_userService.appropriateUser(request.getIdUser(), UserPermission.CURATOR_REPORT_VALIDATE)) {
            Attachment attachment = _reportService.getReportAttachment(id);
            ReportDto result = _reportService.validateReport(id, request.isValidate());
            if (result != null) {
                if (request.isValidate()) {
//                    if (attachment.getPoi() != null) {
//                        attachment.getPoi().getAttachments().remove(attachment);
//                        attachment.setNotNullPoi(null);
//                    }
//                    if (attachment.getItinerary() != null) {
//                        attachment.getItinerary().getAttachments().remove(attachment);
//                        attachment.setNotNullItinerary(null);
//                    }
//                    Report report = ReportMapper.toEntity(result, true);
//                    attachment.getReports().remove(report);
//                    report.setAttachment(null);
//                    _attachmentService.deleteFromRepository(attachment);
                    _attachmentService.deleteFromRepository(attachment);
                }
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
