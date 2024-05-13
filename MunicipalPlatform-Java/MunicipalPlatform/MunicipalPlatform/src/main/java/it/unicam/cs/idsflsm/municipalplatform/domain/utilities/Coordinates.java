package it.unicam.cs.idsflsm.municipalplatform.domain.utilities;

import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidLatitudeException;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidLongitudeException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Coordinates {
    @Column(name = "latitude", nullable = false)
    private double latitude;
    @Column(name = "longitude", nullable = false)
    private double longitude;
    // Method to convert two strings (latitude, longitude) to Coordinates object
    public static Coordinates fromStrings(String latitudeString, String longitudeString) {
        try {
            double latitude = Double.parseDouble(latitudeString);
            double longitude = Double.parseDouble(longitudeString);
            return new Coordinates(latitude, longitude);
        } catch (NumberFormatException e) {
            if (latitudeString == null) {
                throw new InvalidLatitudeException("null");
            } else if (longitudeString == null) {
                throw new InvalidLongitudeException("null");
            } else {
                throw new InvalidLatitudeException(latitudeString);
            }
        }
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Coordinates other = (Coordinates) obj;
        return other.getLatitude() == this.getLatitude()
                && other.getLongitude() == this.getLongitude();
    }
    // Method to convert Coordinates object to string
    @Override
    public String toString() {
        return String.format("(%f, %f)", latitude, longitude);
    }
}
