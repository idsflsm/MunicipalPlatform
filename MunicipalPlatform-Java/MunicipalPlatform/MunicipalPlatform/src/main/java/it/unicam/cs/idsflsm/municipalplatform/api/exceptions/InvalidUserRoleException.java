package it.unicam.cs.idsflsm.municipalplatform.api.exceptions;

public class InvalidUserRoleException extends RuntimeException {
    public InvalidUserRoleException(String text) {
        super("No constant with name " + text + " found in enum UserRole");
    }
}
