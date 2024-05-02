package it.unicam.cs.idsflsm.municipalplatform.application.mappers.contest;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.contest.ContributionDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contribution;

import java.util.List;
import java.util.stream.Collectors;
public class ContributionMapper {
    public static ContributionDto toDto(Contribution contribution) {
        if (contribution != null) {
            return new ContributionDto(
                    contribution.getId(),
                    contribution.getContest(),
                    contribution.getPoi(),
                    contribution.getItinerary(),
                    contribution.getState(),
                    contribution.getResult()
            );
        } else {
            return null;
        }
    }
    public static Contribution toEntity(ContributionDto contributionDto) {
        if (contributionDto != null) {
            return new Contribution(
                    contributionDto.getId(),
                    contributionDto.getContest(),
                    contributionDto.getPoi(),
                    contributionDto.getItinerary(),
                    contributionDto.getState(),
                    contributionDto.getResult()
            );
        } else {
            return null;
        }
    }
    public static List<ContributionDto> toDto(List<Contribution> contributions) {
        if (contributions != null) {
            return contributions.stream().map(ContributionMapper::toDto).collect(Collectors.toList());
        } else {
            return null;
        }
    }
    public static List<Contribution> toEntity(List<ContributionDto> contributionDtos) {
        if (contributionDtos != null) {
            return contributionDtos.stream().map(ContributionMapper::toEntity).collect(Collectors.toList());
        } else {
            return null;
        }
    }
}
