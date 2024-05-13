package it.unicam.cs.idsflsm.municipalplatform.application.models.requests.content.poi;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddPOIRequest extends ModifyPOIRequest {
    public AddPOIRequest() {
    }
    public AddPOIRequest(String name, String latitude, String longitude, String description, String author, String expiryDate) {
        super(name, latitude, longitude, description, author, expiryDate);
    }
}
