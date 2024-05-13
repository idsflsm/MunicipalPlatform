package it.unicam.cs.idsflsm.municipalplatform.api.exceptions;
public class InvalidContentStateException extends RuntimeException {
    public InvalidContentStateException(String text) {
        super("No constant with name " + text + " found in enum ContentState");
    }
}
