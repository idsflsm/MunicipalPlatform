package it.unicam.cs.idsflsm.municipalplatform.api.exceptions;
public class InvalidDateFormatException extends RuntimeException {
    public InvalidDateFormatException() {
        super("Invalid date format. Expected format: day/month/year");
    }
}