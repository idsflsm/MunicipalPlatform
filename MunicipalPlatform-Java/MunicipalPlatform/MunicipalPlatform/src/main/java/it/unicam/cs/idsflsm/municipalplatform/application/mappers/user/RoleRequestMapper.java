package it.unicam.cs.idsflsm.municipalplatform.application.mappers.user;

import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.itinerary.PendingItineraryMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.content.itinerary.PendingItineraryDto;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.RoleRequestDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.PendingItinerary;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.RoleRequest;

import java.util.List;
import java.util.stream.Collectors;

public class RoleRequestMapper {
    public static RoleRequestDto toDto(RoleRequest roleRequest) {
        if (roleRequest != null) {
            return new RoleRequestDto(
                    roleRequest.getUsername(),
                    roleRequest.getRole()
            );
        } else {
            return null;
        }
    }
    public static RoleRequest toEntity(RoleRequestDto roleRequestDto) {
        if (roleRequestDto != null) {
            return new RoleRequest(
                    roleRequestDto.getUsername(),
                    roleRequestDto.getRole()
            );
        } else {
            return null;
        }
    }
    public static List<RoleRequestDto> toDto(List<RoleRequest> roleRequests) {
        if (roleRequests != null) {
            return roleRequests.stream().map(RoleRequestMapper::toDto).collect(Collectors.toList());
        } else {
            return null;
        }
    }
    public static List<RoleRequest> toEntity(List<RoleRequestDto> roleRequestDtos) {
        if (roleRequestDtos != null) {
            return roleRequestDtos.stream().map(RoleRequestMapper::toEntity).collect(Collectors.toList());
        } else {
            return null;
        }
    }
}
