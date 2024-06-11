package it.unicam.cs.idsflsm.municipalplatform.application.mappers.user;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.RoleRequestDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.RoleRequest;

import java.util.List;
import java.util.stream.Collectors;
/**
 * Utility class for mapping between RoleRequest and RoleRequestDto
 */
public class RoleRequestMapper {
    /**
     * Converts a RoleRequest entity to a RoleRequest DTO
     *
     * @param roleRequest the roleRequest entity to be converted
     * @return the corresponding RoleRequestDto if the roleRequest parameter is not null, null otherwise
     */
    public static RoleRequestDto toDto(RoleRequest roleRequest) {
        if (roleRequest != null) {
            return new RoleRequestDto(
                    roleRequest.getIdUser(),
                    roleRequest.getUsername(),
                    roleRequest.getRole()
            );
        }
        return null;
    }
    /**
     * Converts a RoleRequestDto to a RoleRequest entity
     *
     * @param dto the RoleRequestDto to be converted
     * @return the corresponding RoleRequest entity if the dto parameter is not null, null otherwise
     */
    public static RoleRequest toEntity(RoleRequestDto dto) {
        if (dto != null) {
            return new RoleRequest(
                    dto.getIdUser(),
                    dto.getUsername(),
                    dto.getRole()
            );
        }
        return null;
    }
    /**
     * Converts a list of RoleRequest entities to a list of RoleRequest DTOs
     *
     * @param roleRequests the list of RoleRequest entities to be converted
     * @return the corresponding list of RoleRequestDto if the list in the parameter is not null, null otherwise
     */
    public static List<RoleRequestDto> toDto(List<RoleRequest> roleRequests) {
        if (roleRequests != null) {
            return roleRequests.stream().map(RoleRequestMapper::toDto).collect(Collectors.toList());
        }
        return null;
    }
    /**
     * Converts a list of RoleRequest DTOs to a list of RoleRequest entities
     *
     * @param roleRequestDtos the list of RoleRequest DTOs to be converted
     * @return the corresponding list of RoleRequest entities if the list in the parameter is not null, null otherwise
     */
    public static List<RoleRequest> toEntity(List<RoleRequestDto> roleRequestDtos) {
        if (roleRequestDtos != null) {
            return roleRequestDtos.stream().map(RoleRequestMapper::toEntity).collect(Collectors.toList());
        }
        return null;
    }
}
