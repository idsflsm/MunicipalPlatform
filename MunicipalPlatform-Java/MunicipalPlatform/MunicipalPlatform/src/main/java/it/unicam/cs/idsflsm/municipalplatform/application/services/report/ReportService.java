package it.unicam.cs.idsflsm.municipalplatform.application.services.report;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.attachment.IAttachmentService;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.report.IReportService;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.report.ReportMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.report.ReportDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.attachment.Attachment;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.report.Report;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.report.IReportRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
@Service
@Transactional
@AllArgsConstructor(onConstructor_ = @Autowired)
public class ReportService implements IReportService {
    private final IReportRepository _reportRepository;
    @Override
    public void saveInRepository(Report report) {
        _reportRepository.save(report);
    }
    @Override
    public List<ReportDto> getAllReports(Optional<Predicate<Report>> predicate) {
        List<Report> result = predicate.map(poiPredicate -> _reportRepository.findAll()
                        .stream()
                        .filter(poiPredicate)
                        .collect(Collectors.toList()))
                .orElseGet(_reportRepository::findAll);
        if (!result.isEmpty()) {
            return ReportMapper.toDto(result, true);
        } else {
            return null;
        }
    }
    @Override
    public ReportDto getReportById(UUID id) {
        Report report = _reportRepository.findById(id).orElse(null);
        if (report != null) {
            return ReportMapper.toDto(report, true);
        } else {
            return null;
        }
    }
//    @Override
//    public ReportDto addReport(ReportDto reportDto) {
//        if (getReportById(reportDto.getId()) == null) {
//            Report report = ReportMapper.toEntity(reportDto, true);
//            _reportRepository.save(report);
//            return reportDto;
//        } else {
//            return null;
//        }
//    }
//    @Override
//    public ReportDto deleteReportById(UUID id) {
//        Report report = _reportRepository.findById(id).orElse(null);
//        if (report != null) {
//            Attachment attachment = report.getAttachment();
//            report.setAttachment(null);
//            attachment.getReports().remove(report);
//            _reportRepository.delete(report);
//            return ReportMapper.toDto(report, true);
//        } else {
//            return null;
//        }
//    }
//    @Override
//    public boolean deleteReport(ReportDto reportDto, Optional<Predicate<Report>> predicate) {
//        if (getAllReports(predicate).get(0) != null) {
//            Report report = ReportMapper.toEntity(reportDto);
//            assert report != null;
//            _reportRepository.delete(report);
//            return true;
//        } else {
//            return false;
//        }
//    }
    @Override
    public ReportDto validateReport(UUID id, boolean validate) {
        Report report = _reportRepository.findById(id).orElse(null);
        if (report != null) {
            if (!validate) {
                report.getAttachment().getReports().remove(report);
                report.setAttachment(null);
                _reportRepository.delete(report);
            } else {
                Attachment attachment = report.getAttachment();
                if (attachment.getPoi() != null) {
                    attachment.getPoi().getAttachments().remove(attachment);
                }
                if (attachment.getItinerary() != null) {
                    attachment.getItinerary().getAttachments().remove(attachment);
                }
                attachment.setNotNullPoi(null);
                attachment.setNotNullItinerary(null);
                attachment.getReports().remove(report);
                report.setAttachment(null);
                _reportRepository.delete(report);
            }
            return ReportMapper.toDto(report, true);
        } else {
            return null;
        }
    }
    @Override
    public Attachment getReportAttachment(UUID idReport) {
        Report report = _reportRepository.findById(idReport).orElse(null);
        if (report != null) {
            return report.getAttachment();
        } else {
            return null;
        }
    }
}
