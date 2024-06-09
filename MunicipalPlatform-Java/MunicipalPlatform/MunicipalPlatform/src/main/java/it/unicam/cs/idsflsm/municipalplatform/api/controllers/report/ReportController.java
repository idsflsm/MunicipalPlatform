package it.unicam.cs.idsflsm.municipalplatform.api.controllers.report;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.attachment.IAttachmentService;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.report.IReportService;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.user.IUserService;
import it.unicam.cs.idsflsm.municipalplatform.application.criterias.report.ReportCriteria;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.report.ReportDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.requests.report.ValidateReportRequest;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.report.Report;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.UserPermission;
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
    /**
     * The service for Report entity
     */
    private final IReportService _reportService;
    /**
     * The service for User entities
     */
    private final IUserService _userService;

    /**
     * Method that retrieves a list of Report DTOs after filtering
     * @param idUser the UUID of the user performing the operation
     * @param motivation the motivation of desired reports
     * @return the list of found Report DTOs, based on params
     */
    @GetMapping
    public ResponseEntity<?> getAllReports
            (@RequestParam(required = true) UUID idUser,
             @RequestParam(required = false) String motivation) {
        if (_userService.appropriateUser(idUser, UserPermission.CURATOR_REPORT_READ)) {
            Predicate<Report> criterias = ReportCriteria.hasMotivation(motivation);
            List<ReportDto> reportDtos = _reportService.getReports(Optional.of(criterias));
            if (!reportDtos.isEmpty()) {
                return new ResponseEntity<>(reportDtos, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
    /**
     * Method that retrieves a report by its unique identifier
     * @param id the UUID of desired report
     * @param idUser the UUID of the user performing the operation
     * @return the Report DTO if exists
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getReportById(@PathVariable("id") UUID id, @RequestParam UUID idUser) {
        if (_userService.appropriateUser(idUser, UserPermission.CURATOR_REPORT_READ)) {
            ReportDto result = _reportService.getReportById(id);
            if (result != null) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
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

    /**
     * Method that validates a report based on a given validation flag
     * @param id the UUID of desired report
     * @param request the request for validating a report
     * @return the Report DTO if has been validated
     */
    @PatchMapping("/{id}/validate")
    public ResponseEntity<?> validateReport(@PathVariable("id") UUID id, @RequestBody ValidateReportRequest request) {
        if (_userService.appropriateUser(request.getIdUser(), UserPermission.CURATOR_REPORT_VALIDATE)) {
            ReportDto result = _reportService.validateReport(id, request.isValidate());
            if (result != null) {
//                if (request.isValidate()) {
////                    if (attachment.getPoi() != null) {
////                        attachment.getPoi().getAttachments().remove(attachment);
////                        attachment.setNotNullPoi(null);
////                    }
////                    if (attachment.getItinerary() != null) {
////                        attachment.getItinerary().getAttachments().remove(attachment);
////                        attachment.setNotNullItinerary(null);
////                    }
////                    Report report = ReportMapper.toEntity(result, true);
////                    attachment.getReports().remove(report);
////                    report.setAttachment(null);
////                    _attachmentService.deleteFromRepository(attachment);
//                }
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
