package it.unicam.cs.idsflsm.municipalplatform.application.mappers.contest;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.itinerary.GenericItineraryMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.poi.GenericPOIMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.contest.ContributionDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contribution;

import java.util.List;
import java.util.stream.Collectors;
/**
 * Utility class for mapping between Contribution and ContributionDto
 */
public class ContributionMapper {
    /**
     * Converts a Contribution entity to a Contribution DTO
     *
     * @param contribution            the contribution entity to be converted
     * @param includeRelativeEntities flag indicating whether to map the sub-entities
     * @return the corresponding ContributionDto if the contribution parameter is not null, null otherwise
     */
    public static ContributionDto toDto(Contribution contribution, boolean includeRelativeEntities) {
        if (contribution == null) {
            return null;
        }
        ContributionDto dto = new ContributionDto();
        dto.setId(contribution.getId());
        dto.setState(contribution.getState());
        dto.setResult(contribution.getResult());
        if (includeRelativeEntities) {
            dto.setContest(ContestMapper.toDto(contribution.getContest(), false));
            dto.setPoi(GenericPOIMapper.toDto(contribution.getPoi(), false));
            dto.setItinerary(GenericItineraryMapper.toDto(contribution.getItinerary(), false));
        }
        return dto;
    }
    /**
     * Converts a ContestDto to a Contest entity
     *
     * @param dto                     the ContestDto to be converted
     * @param includeRelativeEntities flag indicating whether to map the sub-DTOs
     * @return the corresponding Contest entity if the dto parameter is not null, null otherwise
     */
    public static Contribution toEntity(ContributionDto dto, boolean includeRelativeEntities) {
        if (dto == null) {
            return null;
        }
        Contribution entity = new Contribution();
        entity.setId(dto.getId());
        entity.setState(dto.getState());
        entity.setResult(dto.getResult());
        if (includeRelativeEntities) {
            entity.setContest(ContestMapper.toEntity(dto.getContest(), false));
            entity.setPoi(GenericPOIMapper.toEntity(dto.getPoi(), false));
            entity.setItinerary(GenericItineraryMapper.toEntity(dto.getItinerary(), false));
        }
        return entity;
    }
    /**
     * Converts a list of Contribution entities to a list of Contribution DTOs
     *
     * @param contributions           the list of Contribution entities to be converted
     * @param includeRelativeEntities flag indicating whether to map the sub-entities
     * @return the corresponding list of ContributionDto if the list in the parameter is not null, null otherwise
     */
    public static List<ContributionDto> toDto(List<Contribution> contributions, boolean includeRelativeEntities) {
        if (contributions == null) {
            return null;
        }
        return contributions.stream()
                .map(contribution -> toDto(contribution, includeRelativeEntities))
                .collect(Collectors.toList());
    }
    /**
     * Converts a list of Contribution DTOs to a list of Contribution entities
     *
     * @param contributionDtos        the list of Contribution DTOs to be converted
     * @param includeRelativeEntities flag indicating whether to map the sub-DTOs
     * @return the corresponding list of Contribution if the list in the parameter is not null, null otherwise
     */
    public static List<Contribution> toEntity(List<ContributionDto> contributionDtos, boolean includeRelativeEntities) {
        if (contributionDtos == null) {
            return null;
        }
        return contributionDtos.stream()
                .map(dto -> toEntity(dto, includeRelativeEntities))
                .collect(Collectors.toList());
    }
}
