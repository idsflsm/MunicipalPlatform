package it.unicam.cs.idsflsm.municipalplatform.api.exceptions;
/**
 * Exception thrown when an invalid ContestResult value is detected
 */
public class InvalidContestResultException extends RuntimeException {
    /**
     * Constructs a new InvalidContestResultException with a detailed message
     * @param text the name of the invalid ContestResult value
     */
    public InvalidContestResultException(String text) {
        super("No constant with name " + text + " found in enum ContestResult");
    }
}
