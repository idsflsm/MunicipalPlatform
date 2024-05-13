package it.unicam.cs.idsflsm.municipalplatform.application.models.requests.content.poi;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePOIRequest extends ModifyPOIRequest {
    public UpdatePOIRequest() {
    }
    public UpdatePOIRequest(String name, String latitude, String longitude, String description, String author, String expiryDate) {
        super(name, latitude, longitude, description, author, expiryDate);
    }
}
