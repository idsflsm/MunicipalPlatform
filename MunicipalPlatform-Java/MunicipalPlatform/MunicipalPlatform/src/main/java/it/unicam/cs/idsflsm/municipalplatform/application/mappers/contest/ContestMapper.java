package it.unicam.cs.idsflsm.municipalplatform.application.mappers.contest;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.user.authenticated.AuthenticatedTouristMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.user.authenticated.GenericAuthenticatedUserMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.contest.ContestDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contest;

import java.util.List;
import java.util.stream.Collectors;
public class ContestMapper {

    public static ContestDto toDto(Contest contest, boolean includeRelativeEntities) {
        if (contest == null) {
            return null;
        }

        ContestDto dto = new ContestDto();
        dto.setId(contest.getId());
        dto.setName(contest.getName());
        dto.setAuthor(contest.getAuthor());
        dto.setDescription(contest.getDescription());
        dto.setCreationDate(contest.getCreationDate());
        dto.setExpiryDate(contest.getExpiryDate());
        dto.setHasWinner(contest.isHasWinner());

        if (includeRelativeEntities) {
            dto.setContributions(ContributionMapper.toDto(contest.getContributions(), false));
            dto.setParticipatingUsers(GenericAuthenticatedUserMapper.toDto(contest.getParticipatingUsers(), false));
        }

        return dto;
    }

    public static Contest toEntity(ContestDto dto, boolean includeRelativeEntities) {
        if (dto == null) {
            return null;
        }

        Contest entity = new Contest();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setAuthor(dto.getAuthor());
        entity.setDescription(dto.getDescription());
        entity.setCreationDate(dto.getCreationDate());
        entity.setExpiryDate(dto.getExpiryDate());
        entity.setHasWinner(dto.isHasWinner());

        if (includeRelativeEntities) {
            entity.setContributions(ContributionMapper.toEntity(dto.getContributions(), false));
            entity.setParticipatingUsers(GenericAuthenticatedUserMapper.toEntity(dto.getParticipatingUsers(), false));
        }

        return entity;
    }

    public static List<ContestDto> toDto(List<Contest> contests, boolean includeRelativeEntities) {
        if (contests == null) {
            return null;
        }

        return contests.stream()
                .map(contest -> toDto(contest, includeRelativeEntities))
                .collect(Collectors.toList());
    }

    public static List<Contest> toEntity(List<ContestDto> contestDtos, boolean includeRelativeEntities) {
        if (contestDtos == null) {
            return null;
        }

        return contestDtos.stream()
                .map(dto -> toEntity(dto, includeRelativeEntities))
                .collect(Collectors.toList());
    }
}
