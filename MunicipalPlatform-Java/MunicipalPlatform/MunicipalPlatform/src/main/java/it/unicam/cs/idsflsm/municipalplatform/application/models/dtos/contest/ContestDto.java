package it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.contest;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import it.unicam.cs.idsflsm.municipalplatform.application.models.dtos.user.authenticated.AuthenticatedUserDto;
import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContestDto {
    private UUID id = UUID.randomUUID();
    private String name;
    private String author;
    private String description;
    private Date creationDate;
    private Date expiryDate;
    private boolean hasWinner = false;
    @JsonManagedReference
    private List<ContributionDto> contributions = new ArrayList<ContributionDto>();
    @JsonManagedReference
    private List<AuthenticatedUserDto> participatingUsers = new ArrayList<AuthenticatedUserDto>();
}
