package it.unicam.cs.idsflsm.municipalplatform.api.exceptions;
public class InvalidContestResultException extends RuntimeException {
    public InvalidContestResultException(String text) {
        super("No constant with name " + text + " found in enum ContestResult");
    }
}
