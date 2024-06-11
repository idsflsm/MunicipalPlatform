package it.unicam.cs.idsflsm.municipalplatform.api.exceptions;
/**
 * Exception thrown when an invalid UserRole value is detected
 */
public class InvalidUserRoleException extends RuntimeException {
    /**
     * Constructs a new InvalidUserRoleException with a detailed message
     *
     * @param text the name of the invalid UserRole value
     */
    public InvalidUserRoleException(String text) {
        super("No constant with name " + text + " found in enum UserRole");
    }
}
