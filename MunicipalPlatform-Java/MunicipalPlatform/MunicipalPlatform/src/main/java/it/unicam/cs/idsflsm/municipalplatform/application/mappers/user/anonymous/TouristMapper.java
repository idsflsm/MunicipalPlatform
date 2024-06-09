package it.unicam.cs.idsflsm.municipalplatform.application.mappers.user.anonymous;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.anonymous.TouristDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.anonymous.Tourist;
/**
 * Utility class for mapping between Tourist and TouristDto
 */
public class TouristMapper {
    /**
     * Converts a Tourist entity to a Tourist DTO
     * @param tourist the tourist entity to be converted
     * @return the corresponding TouristDto if the tourist parameter is not null, null otherwise
     */
    public static TouristDto toDto(Tourist tourist) {
        if (tourist != null) {
            return new TouristDto(
                    tourist.getId()
            );
        }
        return null;
    }
    /**
     * Converts a RoleRequestDto to a RoleRequest entity
     * @param dto the RoleRequestDto to be converted
     * @return the corresponding RoleRequest entity if the dto parameter is not null, null otherwise
     */
    public static Tourist toEntity(TouristDto dto) {
        if (dto != null) {
            return new Tourist(
                    dto.getId()
            );
        }
        return null;
    }
}
