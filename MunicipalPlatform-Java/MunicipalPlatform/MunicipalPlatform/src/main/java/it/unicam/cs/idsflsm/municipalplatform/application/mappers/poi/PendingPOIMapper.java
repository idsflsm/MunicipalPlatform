package it.unicam.cs.idsflsm.municipalplatform.application.mappers.poi;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.poi.PendingPOIDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.PendingPOI;
import java.util.List;
import java.util.stream.Collectors;
public class PendingPOIMapper {
    public static PendingPOIDto toDto(PendingPOI poi) {
        if (poi != null) {
            return new PendingPOIDto(
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
    public static PendingPOI toEntity(PendingPOIDto poiDto) {
        if (poiDto != null) {
            return new PendingPOI(
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
    public static List<PendingPOIDto> toDto(List<PendingPOI> pois) {
        if (pois != null) {
            return pois.stream().map(PendingPOIMapper::toDto).collect(Collectors.toList());
        } else {
            return null;
        }
    }
    public static List<PendingPOI> toEntity(List<PendingPOIDto> poiDtos) {
        if (poiDtos != null) {
            return poiDtos.stream().map(PendingPOIMapper::toEntity).collect(Collectors.toList());
        } else {
            return null;
        }
    }
}
