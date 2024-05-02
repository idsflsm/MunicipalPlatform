package it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.contest;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.contest.Contribution;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.user.authenticated.AuthenticatedTourist;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContestDto {
    private UUID id;
    private String name;
    private String author;
    private String description;
    private Date creationDate;
    private Date expiryDate;
    private boolean hasWinner;
    private List<Contribution> contributions;
    private List<AuthenticatedTourist> participatingTourists;
}
