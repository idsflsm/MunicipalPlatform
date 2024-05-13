package it.unicam.cs.idsflsm.municipalplatform.application.models.requests.content.itinerary;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class UpdateItineraryRequest extends ModifyItineraryRequest {
    public UpdateItineraryRequest() {
    }
    public UpdateItineraryRequest(String name, String latitude, String longitude, String description, String author, String expiryDate) {
        super(name, latitude, longitude, description, author, expiryDate);
    }
}
