package it.unicam.cs.idsflsm.municipalplatform.application.mappers.user.authenticated;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.CuratorDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.Curator;
public class CuratorMapper {
    public static CuratorDto toDto(Curator curator) {
        return new CuratorDto(
                curator.getId(),
                curator.getUsername(),
                curator.getPassword(),
                curator.getName(),
                curator.getSurname(),
                curator.getPois(),
                curator.getItineraries()
        );
    }
    public static Curator toEntity(CuratorDto curatorDto) {
        return new Curator(
                curatorDto.getId(),
                curatorDto.getUsername(),
                curatorDto.getPassword(),
                curatorDto.getName(),
                curatorDto.getSurname(),
                curatorDto.getPois(),
                curatorDto.getItineraries()
        );
    }
}
