package it.unicam.cs.idsflsm.municipalplatform.application.mappers.report;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.attachment.GenericAttachmentMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.report.ReportDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.report.Report;

import java.util.List;
import java.util.stream.Collectors;
public class ReportMapper {
    public static ReportDto toDto(Report report, boolean includeRelativeEntities) {
        if (report != null) {
            ReportDto dto = new ReportDto();
            dto.setId(report.getId());
            dto.setMotivation(report.getMotivation());
            if (includeRelativeEntities) {
                dto.setAttachment(GenericAttachmentMapper.toDto(report.getAttachment(), false));
            }
            return dto;
        } else {
            return null;
        }
    }
    public static Report toEntity(ReportDto reportDto, boolean includeRelativeEntities) {
        if (reportDto != null) {
            Report entity = new Report();
            entity.setId(reportDto.getId());
            entity.setMotivation(reportDto.getMotivation());
            if (includeRelativeEntities) {
                entity.setAttachment(GenericAttachmentMapper.toEntity(reportDto.getAttachment(), false));
            }
            return entity;
        } else {
            return null;
        }
    }
    public static List<ReportDto> toDto(List<Report> reports, boolean includeRelativeEntities) {
        if (reports != null) {
            return reports.stream().map(report -> ReportMapper.toDto(report, includeRelativeEntities)).collect(Collectors.toList());
        } else {
            return null;
        }
    }
    public static List<Report> toEntity(List<ReportDto> reportDtos, boolean includeRelativeEntities) {
        if (reportDtos != null) {
            return reportDtos.stream().map(reportDto -> ReportMapper.toEntity(reportDto, includeRelativeEntities)).collect(Collectors.toList());
        } else {
            return null;
        }
    }
}
