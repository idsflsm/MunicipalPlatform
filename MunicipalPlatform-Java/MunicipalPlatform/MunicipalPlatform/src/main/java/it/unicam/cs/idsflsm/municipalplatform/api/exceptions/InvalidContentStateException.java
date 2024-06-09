package it.unicam.cs.idsflsm.municipalplatform.api.exceptions;
/**
 * Exception thrown when an invalid ContentState value is detected
 */
public class InvalidContentStateException extends RuntimeException {
    /**
     * Constructs a new InvalidContentStateException and detailed message
     * @param text the name of the invalid ContentState value
     */
    public InvalidContentStateException(String text) {
        super("No constant with name " + text + " found in enum ContentState");
    }
}
