package it.unicam.cs.idsflsm.municipalplatform.domain.utilities;
import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidDateFormatException;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
/**
 * Represents the creation date/expiry date of a content uploaded to the platform
 */
@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Date {
    /**
     * Represents the day of the month, an integer value between 1 and 31, inclusive.
     */
    private int day;
    /**
     * Represents the month of the year, an integer value between 1 and 12, inclusive.
     */
    private int month;
    /**
     * Represents the year in the Gregorian calendar, an integer value,
     * typically four digits long.
     */
    private int year;
    /**
     * Represents the value of Date considered maximum, set a priori
     */
    public static final Date DATE_MAX = new Date(31, 12, 2070);
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Date other = (Date) obj;
        return other.getDay() == this.getDay()
                && other.getMonth() == this.getMonth()
                && other.getYear() == this.getYear();
    }
    /**
     * Method for converting a string to a Date value
     *
     * @param dateString string value to be converted
     * @return a new Date object if the parsing of the strings is successful
     * @throws InvalidDateFormatException if dateString does not respect the Date format set,
     *                                    or if it corresponds to a value prior to the current date
     */
    public static Date fromString(String dateString) throws InvalidDateFormatException {
        if (dateString == null || dateString.isBlank()) {
            return DATE_MAX;
        } else {
            String[] parts = dateString.split("/");
            if (parts.length != 3) {
                throw new InvalidDateFormatException("Invalid date format. " +
                        "Expected format: day/month/year");
            }
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);
            Date result = new Date(day, month, year);
            if (result.beforeThan(toDate(LocalDate.now()))) {
                throw new InvalidDateFormatException("Invalid date value. " +
                        "Cannot insert a date value before the current date");
            }
            return result;
        }
    }
    @Override
    public String toString() {
        return String.format("%02d/%02d/%d", this.day, this.month, this.year);
    }
    /**
     * Method to convert a Date object into a LocalDate one
     *
     * @return corresponding LocalDate value, based on the data of the object invoking the method
     */
    public LocalDate toLocalDate() {
        return LocalDate.of(this.year, this.month, this.day);
    }
    /**
     * Method to convert a LocalDate object into a Date one
     *
     * @param localDate the value to be converted
     * @return a new Date object, based on localDate data
     */
    public static Date toDate(LocalDate localDate) {
        return new Date(localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear());
    }
    /**
     * Method that checks if the date of the object invoking the method
     * corresponds to a value prior to the date in the parameter
     *
     * @param date the Date value to be used for comparison
     * @return true if the date of the invoking object is before than one
     * in the parameter, false otherwise
     */
    public boolean beforeThan(Date date) {
        return this.toLocalDate().isBefore(date.toLocalDate());
    }
}
