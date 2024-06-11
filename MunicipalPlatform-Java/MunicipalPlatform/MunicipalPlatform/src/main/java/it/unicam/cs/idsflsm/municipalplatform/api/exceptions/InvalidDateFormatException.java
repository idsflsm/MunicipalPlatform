package it.unicam.cs.idsflsm.municipalplatform.api.exceptions;
/**
 * Exception thrown when an invalid Date value is detected
 */
public class InvalidDateFormatException extends RuntimeException {
    /**
     * Constructs a new InvalidDateException with a detailed message
     *
     * @param message the detail message explaining the invalid Date value
     */
    public InvalidDateFormatException(String message) {
        super(message);
    }
}