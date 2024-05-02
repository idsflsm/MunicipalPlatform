package it.unicam.cs.idsflsm.municipalplatform.application.services.report;
import it.unicam.cs.idsflsm.municipalplatform.application.abstractions.services.report.IReportService;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.report.ReportMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.report.ReportDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.report.Report;
import it.unicam.cs.idsflsm.municipalplatform.infrastructure.repositories.report.IReportRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
@Service
@AllArgsConstructor
public class ReportService implements IReportService {
    private final IReportRepository _reportRepository;
    @Override
    public List<ReportDto> getAllReports(Optional<Predicate<Report>> predicate) {
        List<Report> result = predicate.map(poiPredicate -> _reportRepository.findAll()
                        .stream()
                        .filter(poiPredicate)
                        .collect(Collectors.toList()))
                .orElseGet(_reportRepository::findAll);
        if (!result.isEmpty()) {
            return ReportMapper.toDto(result);
        } else {
            return null;
        }
    }
    @Override
    public ReportDto getReportById(UUID id) {
        Report report = _reportRepository.findById(id).orElse(null);
        if (report != null) {
            return ReportMapper.toDto(report);
        } else {
            return null;
        }
    }
    @Override
    public boolean addReport(ReportDto reportDto) {
        if (getReportById(reportDto.getId()) == null) {
            Report report = ReportMapper.toEntity(reportDto);
            _reportRepository.save(report);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean deleteReportById(UUID id) {
        if (getReportById(id) != null) {
            _reportRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean deleteReport(ReportDto reportDto, Optional<Predicate<Report>> predicate) {
        if (getAllReports(predicate).get(0) != null) {
            Report report = ReportMapper.toEntity(reportDto);
            assert report != null;
            _reportRepository.delete(report);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean updateReport(ReportDto reportDto, Optional<Predicate<Report>> predicate) {
        if (getAllReports(predicate).get(0) != null) {
            Report report = ReportMapper.toEntity(reportDto);
            assert report != null;
            _reportRepository.save(report);
            return true;
        } else {
            return false;
        }
    }
}
