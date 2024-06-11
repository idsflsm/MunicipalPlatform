package it.unicam.cs.idsflsm.municipalplatform.application.services.report;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.report.IReportService;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.report.ReportMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.report.ReportDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.report.Report;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.attachment.IAttachmentRepository;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.report.IReportRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
/**
 * Service class for the ReportRepository. It provides methods to manipulate persistent
 * reports in the database
 */
@Service
@Transactional
@AllArgsConstructor(onConstructor_ = @Autowired)
public class ReportService implements IReportService {
    /**
     * The repository for Report entity
     */
    private final IReportRepository _reportRepository;
    /**
     * The repository for Attachment entity
     */
    private final IAttachmentRepository _attachmentRepository;
    @Override
    public void saveInRepository(Report report) {
        _reportRepository.save(report);
    }
    @Override
    public List<ReportDto> getReports(Optional<Predicate<Report>> predicate) {
        List<Report> result = predicate.map(reportPredicate -> _reportRepository.findAll()
                        .stream()
                        .filter(reportPredicate)
                        .toList())
                .orElseGet(_reportRepository::findAll);
        if (!result.isEmpty()) {
            return ReportMapper.toDto(result, true);
        }
        return new ArrayList<>();
    }
    @Override
    public ReportDto getReportById(UUID id) {
        Report report = _reportRepository.findById(id).orElse(null);
        if (report != null) {
            return ReportMapper.toDto(report, true);
        }
        return null;
    }
    @Override
    public ReportDto validateReport(UUID id, boolean validate) {
        Report report = _reportRepository.findById(id).orElse(null);
        if (report != null) {
            if (!validate) {
                report.detachFromEntities();
                _reportRepository.delete(report);
            } else {
                Attachment attachment = report.getAttachment();
                attachment.detachFromEntities();
                report.detachFromEntities();
                _attachmentRepository.delete(attachment);
                _reportRepository.delete(report);
            }
            return ReportMapper.toDto(report, true);
        }
        return null;
    }
}
