package it.unicam.cs.idsflsm.municipalplatform.application.mappers.contest;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.user.authenticated.GenericAuthenticatedUserMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.contest.ContestDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contest;

import java.util.List;
import java.util.stream.Collectors;
/**
 * Utility class for mapping between Contest and ContestDto
 */
public class ContestMapper {
    /**
     * Converts a Contest entity to a Contest DTO
     *
     * @param contest                 the contest entity to be converted
     * @param includeRelativeEntities flag indicating whether to map the sub-entities
     * @return the corresponding ContestDto if the contest parameter is not null, null otherwise
     */
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
    /**
     * Converts a ContestDto to a Contest entity
     *
     * @param dto                     the ContestDto to be converted
     * @param includeRelativeEntities flag indicating whether to map the sub-DTOs
     * @return the corresponding Contest entity if the dto parameter is not null, null otherwise
     */
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
    /**
     * Converts a list of Contest entities to a list of Contest DTOs
     *
     * @param contests                the list of Contest entities to be converted
     * @param includeRelativeEntities flag indicating whether to map the sub-entities
     * @return the corresponding list of ContestDto if the list in the parameter is not null, null otherwise
     */
    public static List<ContestDto> toDto(List<Contest> contests, boolean includeRelativeEntities) {
        if (contests == null) {
            return null;
        }
        return contests.stream()
                .map(contest -> toDto(contest, includeRelativeEntities))
                .collect(Collectors.toList());
    }
    /**
     * Converts a list of Contest DTOs to a list of Contest entities
     *
     * @param contestDtos             the list of Contest DTOs to be converted
     * @param includeRelativeEntities flag indicating whether to map the sub-DTOs
     * @return the corresponding list of Contest if the list in the parameter is not null, null otherwise
     */
    public static List<Contest> toEntity(List<ContestDto> contestDtos, boolean includeRelativeEntities) {
        if (contestDtos == null) {
            return null;
        }
        return contestDtos.stream()
                .map(dto -> toEntity(dto, includeRelativeEntities))
                .collect(Collectors.toList());
    }
}
