package it.unicam.cs.idsflsm.municipalplatform.api.exceptions;
/**
 * Exception thrown when an invalid Latitude value is detected
 */
public class InvalidLatitudeException extends RuntimeException {
    /**
     * Constructs a new InvalidLatitudeException with a detailed message
     *
     * @param message the detail message explaining the invalid Latitude value
     */
    public InvalidLatitudeException(String message) {
        super(message);
    }
}
