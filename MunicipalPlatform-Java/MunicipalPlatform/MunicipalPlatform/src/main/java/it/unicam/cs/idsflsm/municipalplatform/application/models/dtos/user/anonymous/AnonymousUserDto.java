package it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.anonymous;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class AnonymousUserDto {
    private UUID id = UUID.randomUUID();
}
