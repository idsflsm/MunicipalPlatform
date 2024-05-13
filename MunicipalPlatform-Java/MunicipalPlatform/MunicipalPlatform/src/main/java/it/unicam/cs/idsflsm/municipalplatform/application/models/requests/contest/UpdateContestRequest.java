package it.unicam.cs.idsflsm.municipalplatform.application.models.requests.contest;

import it.unicam.cs.idsflsm.municipalplatform.domain.utilities.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateContestRequest extends ModifyContestRequest {
    public UpdateContestRequest() {
    }
    public UpdateContestRequest(String name, String author, String description, String expiryDate) {
        super(name, author, description, expiryDate);
    }
}
