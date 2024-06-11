package it.unicam.cs.idsflsm.municipalplatform.application.mappers.report;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.attachment.GenericAttachmentMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.report.ReportDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.report.Report;

import java.util.List;
import java.util.stream.Collectors;
/**
 * Utility class for mapping between Report and ReportDto
 */
public class ReportMapper {
    /**
     * Converts a Report entity to a Report DTO
     *
     * @param report                  the report entity to be converted
     * @param includeRelativeEntities flag indicating whether to map the sub-entities
     * @return the corresponding ReportDto if the report parameter is not null, null otherwise
     */
    public static ReportDto toDto(Report report, boolean includeRelativeEntities) {
        if (report != null) {
            ReportDto dto = new ReportDto();
            dto.setId(report.getId());
            dto.setMotivation(report.getMotivation());
            if (includeRelativeEntities) {
                dto.setAttachment(GenericAttachmentMapper.toDto(report.getAttachment(), false));
            }
            return dto;
        }
        return null;
    }
    /**
     * Converts a ReportDto to a Report entity
     *
     * @param dto                     the ReportDto to be converted
     * @param includeRelativeEntities flag indicating whether to map the sub-DTOs
     * @return the corresponding Report entity if the dto parameter is not null, null otherwise
     */
    public static Report toEntity(ReportDto dto, boolean includeRelativeEntities) {
        if (dto != null) {
            Report entity = new Report();
            entity.setId(dto.getId());
            entity.setMotivation(dto.getMotivation());
            if (includeRelativeEntities) {
                entity.setAttachment(GenericAttachmentMapper.toEntity(dto.getAttachment(), false));
            }
            return entity;
        }
        return null;
    }
    /**
     * Converts a list of Report entities to a list of Report DTOs
     *
     * @param reports                 the list of Report entities to be converted
     * @param includeRelativeEntities flag indicating whether to map the sub-entities
     * @return the corresponding list of ReportDto if the list in the parameter is not null, null otherwise
     */
    public static List<ReportDto> toDto(List<Report> reports, boolean includeRelativeEntities) {
        if (reports != null) {
            return reports.stream().map(report -> ReportMapper.toDto(report, includeRelativeEntities)).collect(Collectors.toList());
        }
        return null;
    }
    /**
     * Converts a list of Report DTOs to a list of Report entities
     *
     * @param reportDtos              the list of Report DTOs to be converted
     * @param includeRelativeEntities flag indicating whether to map the sub-DTOs
     * @return the corresponding list of Report if the list in the parameter is not null, null otherwise
     */
    public static List<Report> toEntity(List<ReportDto> reportDtos, boolean includeRelativeEntities) {
        if (reportDtos != null) {
            return reportDtos.stream().map(reportDto -> ReportMapper.toEntity(reportDto, includeRelativeEntities)).collect(Collectors.toList());
        }
        return null;
    }
}
