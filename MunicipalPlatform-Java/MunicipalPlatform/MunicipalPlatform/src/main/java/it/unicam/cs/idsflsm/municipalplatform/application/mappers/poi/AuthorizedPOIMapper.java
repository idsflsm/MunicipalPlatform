package it.unicam.cs.idsflsm.municipalplatform.application.mappers.poi;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.poi.AuthorizedPOIDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.AuthorizedPOI;
import java.util.List;
import java.util.stream.Collectors;
public class AuthorizedPOIMapper {
    public static AuthorizedPOIDto toDto(AuthorizedPOI poi) {
        if (poi != null) {
            return new AuthorizedPOIDto(
                    poi.getId(),
                    poi.getName(),
                    poi.getCoordinates(),
                    poi.getDescription(),
                    poi.getAuthor(),
                    poi.getCreationDate(),
                    poi.getExpiryDate(),
                    poi.getState(),
                    poi.getPoiItineraries(),
                    poi.getTourists(),
                    poi.getAttachments()
            );
        } else {
            return null;
        }
    }
    public static AuthorizedPOI toEntity(AuthorizedPOIDto poiDto) {
        if (poiDto != null) {
            return new AuthorizedPOI(
                    poiDto.getId(),
                    poiDto.getName(),
                    poiDto.getCoordinates(),
                    poiDto.getDescription(),
                    poiDto.getAuthor(),
                    poiDto.getCreationDate(),
                    poiDto.getExpiryDate(),
                    poiDto.getState(),
                    poiDto.getPoiItineraries(),
                    poiDto.getTourists(),
                    poiDto.getAttachments()
            );
        } else {
            return null;
        }
    }
    public static List<AuthorizedPOIDto> toDto(List<AuthorizedPOI> pois) {
        if (pois != null) {
            return pois.stream().map(AuthorizedPOIMapper::toDto).collect(Collectors.toList());
        } else {
            return null;
        }
    }
    public static List<AuthorizedPOI> toEntity(List<AuthorizedPOIDto> poiDtos) {
        if (poiDtos != null) {
            return poiDtos.stream().map(AuthorizedPOIMapper::toEntity).collect(Collectors.toList());
        } else {
            return null;
        }
    }
}
