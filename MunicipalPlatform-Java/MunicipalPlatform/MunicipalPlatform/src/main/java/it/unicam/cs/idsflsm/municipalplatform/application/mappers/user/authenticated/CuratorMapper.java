package it.unicam.cs.idsflsm.municipalplatform.application.mappers.user.authenticated;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.itinerary.GenericItineraryMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.content.poi.GenericPOIMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.mappers.contest.ContestMapper;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.CuratorDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.Curator;
public class CuratorMapper {

    public static CuratorDto toDto(Curator curator, boolean includeRelativeEntities) {
        if (curator == null) {
            return null;
        }

        CuratorDto dto = new CuratorDto();
        dto.setId(curator.getId());
        dto.setUsername(curator.getUsername());
        dto.setPassword(curator.getPassword());
        dto.setName(curator.getName());
        dto.setSurname(curator.getSurname());
        dto.setRole(curator.getRole());

        if (includeRelativeEntities) {
            dto.setPois(GenericPOIMapper.toDto(curator.getPois(), false));
            dto.setItineraries(GenericItineraryMapper.toDto(curator.getItineraries(), false));
            dto.setParticipatedContests(ContestMapper.toDto(curator.getParticipatedContests(), false));
        }

        return dto;
    }

    public static Curator toEntity(CuratorDto dto, boolean includeRelativeEntities) {
        if (dto == null) {
            return null;
        }

        Curator entity = new Curator();
        entity.setId(dto.getId());
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setRole(dto.getRole());

        if (includeRelativeEntities) {
            entity.setPois(GenericPOIMapper.toEntity(dto.getPois(), false));
            entity.setItineraries(GenericItineraryMapper.toEntity(dto.getItineraries(), false));
            entity.setParticipatedContests(ContestMapper.toEntity(dto.getParticipatedContests(), false));
        }

        return entity;
    }
}

