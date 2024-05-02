package it.unicam.cs.idsflsm.municipalplatform.application.mappers.user.anonymous;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.anonymous.TouristDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.anonymous.Tourist;
public class TouristMapper {
    public static TouristDto toDto(Tourist tourist) {
        return new TouristDto(
                tourist.getId()
        );
    }
    public static Tourist toTourist(TouristDto touristDto) {
        return new Tourist(
                touristDto.getId()
        );
    }
}
