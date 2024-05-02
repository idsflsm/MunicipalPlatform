package it.unicam.cs.idsflsm.municipalplatform.application.mappers.report;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.report.ReportDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.report.Report;

import java.util.List;
import java.util.stream.Collectors;
public class ReportMapper {
    public static ReportDto toDto(Report report) {
        if (report != null) {
            return new ReportDto(
                    report.getId(),
                    report.getMotivation(),
                    report.getAttachment()
            );
        } else {
            return null;
        }
    }
    public static Report toEntity(ReportDto reportDto) {
        if (reportDto != null) {
            return new Report(
                    reportDto.getId(),
                    reportDto.getMotivation(),
                    reportDto.getAttachment()
            );
        } else {
            return null;
        }
    }
    public static List<ReportDto> toDto(List<Report> reports) {
        if (reports != null) {
            return reports.stream().map(ReportMapper::toDto).collect(Collectors.toList());
        } else {
            return null;
        }
    }
    public static List<Report> toEntity(List<ReportDto> reportDtos) {
        if (reportDtos != null) {
            return reportDtos.stream().map(ReportMapper::toEntity).collect(Collectors.toList());
        } else {
            return null;
        }
    }
}
