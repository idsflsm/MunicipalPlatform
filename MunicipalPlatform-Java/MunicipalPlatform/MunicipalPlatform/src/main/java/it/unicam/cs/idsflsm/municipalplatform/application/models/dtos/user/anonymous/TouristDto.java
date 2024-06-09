package it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.anonymous;
import java.util.UUID;
/**
 * Represents a DTO related to the entity Tourist.
 * It contains all fields belonging to the entity
 */
public class TouristDto extends AnonymousUserDto {
    public TouristDto() {
    }
    public TouristDto(UUID id) {
        super(id);
    }
}
