package it.unicam.cs.idsflsm.municipalplatform.domain.utilities;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidLatitudeException;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidLongitudeException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * Represents the coordinates (latitude and longitude) on a map
 * of a content present on the platform
 */
@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Coordinates {
    /**
     * Represents the latitude of the content on the map
     */
    @Column(name = "latitude", nullable = false)
    private double latitude;
    /**
     * Represents the longitude of the content on the map
     */
    @Column(name = "longitude", nullable = false)
    private double longitude;
    /**
     * Method to convert two strings, representing latitude and longitude,
     * into a Coordinates value
     *
     * @param latitudeString  value that represents the latitude
     * @param longitudeString value that represents the longitude
     * @return a new Coordinates object if the parsing of the strings is successful
     * @throws InvalidLatitudeException  if latitudeString is null, out of range
     *                                   or generally invalid
     * @throws InvalidLongitudeException if longitudeString is null, out of range
     *                                   or generally invalid
     */
    public static Coordinates fromStrings(String latitudeString, String longitudeString)
            throws InvalidLatitudeException, InvalidLongitudeException {
        try {
            if (latitudeString == null) {
                throw new InvalidLatitudeException("Null latitude value");
            }
            if (longitudeString == null) {
                throw new InvalidLongitudeException("Null longitude value");
            }
            double latitude = Double.parseDouble(latitudeString);
            double longitude = Double.parseDouble(longitudeString);
            if (latitude < -90 || latitude > 90) {
                throw new InvalidLatitudeException("Latitude out of range: " + latitude);
            }
            if (longitude < -180 || longitude > 180) {
                throw new InvalidLongitudeException("Longitude out of range: " + longitude);
            }
            return new Coordinates(latitude, longitude);
        } catch (NumberFormatException e) {
            if (latitudeString != null && !latitudeString.isEmpty() && !latitudeString.equals("null")) {
                throw new InvalidLatitudeException("Invalid latitude value: " + latitudeString);
            } else if (longitudeString != null && !longitudeString.isEmpty() && !longitudeString.equals("null")) {
                throw new InvalidLongitudeException("Invalid longitude value: " + longitudeString);
            } else {
                throw new InvalidLatitudeException("Invalid input values");
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
    @Override
    public String toString() {
        return String.format("(%f, %f)", this.latitude, this.longitude);
    }
}
