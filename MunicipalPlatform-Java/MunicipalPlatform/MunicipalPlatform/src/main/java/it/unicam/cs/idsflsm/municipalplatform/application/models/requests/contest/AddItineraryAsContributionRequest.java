package it.unicam.cs.idsflsm.municipalplatform.application.models.requests.contest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddItineraryAsContributionRequest extends ModifyContentAsContributionRequest {
    public AddItineraryAsContributionRequest() {
    }
    public AddItineraryAsContributionRequest(String name, String latitude, String longitude, String description, String author, String expiryDate) {
        super(name, latitude, longitude, description, author, expiryDate);
    }
}
