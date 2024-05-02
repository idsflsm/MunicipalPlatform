package it.unicam.cs.idsflsm.municipalplatform.application.mappers.contest;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.contest.ContestDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contest;

import java.util.List;
import java.util.stream.Collectors;
public class ContestMapper {
    public static ContestDto toDto(Contest contest) {
        if (contest != null) {
            return new ContestDto(
                    contest.getId(),
                    contest.getName(),
                    contest.getAuthor(),
                    contest.getDescription(),
                    contest.getCreationDate(),
                    contest.getExpiryDate(),
                    contest.isHasWinner(),
                    contest.getContributions(),
                    contest.getParticipatingTourists()
            );
        } else {
            return null;
        }
    }
    public static Contest toEntity(ContestDto contestDto) {
        if (contestDto != null) {
            return new Contest(
                    contestDto.getId(),
                    contestDto.getName(),
                    contestDto.getAuthor(),
                    contestDto.getDescription(),
                    contestDto.getCreationDate(),
                    contestDto.getExpiryDate(),
                    contestDto.isHasWinner(),
                    contestDto.getContributions(),
                    contestDto.getParticipatingTourists()
            );
        } else {
            return null;
        }
    }
    public static List<ContestDto> toDto(List<Contest> contests) {
        if (contests != null) {
            return contests.stream().map(ContestMapper::toDto).collect(Collectors.toList());
        } else {
            return null;
        }
    }
    public static List<Contest> toEntity(List<ContestDto> contestDtos) {
        if (contestDtos != null) {
            return contestDtos.stream().map(ContestMapper::toEntity).collect(Collectors.toList());
        } else {
            return null;
        }
    }
}
