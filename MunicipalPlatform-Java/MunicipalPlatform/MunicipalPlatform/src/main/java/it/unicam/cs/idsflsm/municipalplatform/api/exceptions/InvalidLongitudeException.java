package it.unicam.cs.idsflsm.municipalplatform.api.exceptions;
/**
 * Exception thrown when an invalid Longitude value is detected
 */
public class InvalidLongitudeException extends RuntimeException {
    /**
     * Constructs a new InvalidLongitudeException with a detailed message
     *
     * @param message the detail message explaining the invalid Longitude value
     */
    public InvalidLongitudeException(String message) {
        super(message);
    }
}
