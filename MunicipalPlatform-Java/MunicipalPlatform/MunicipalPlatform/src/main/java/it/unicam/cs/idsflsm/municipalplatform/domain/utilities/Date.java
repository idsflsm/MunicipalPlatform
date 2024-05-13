package it.unicam.cs.idsflsm.municipalplatform.domain.utilities;

import it.unicam.cs.idsflsm.municipalplatform.api.exceptions.InvalidDateFormatException;
import it.unicam.cs.idsflsm.municipalplatform.domain.entities.content.itinerary.Itinerary;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Date {
    private int day;
    private int month;
    private int year;
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
    public static Date fromString(String dateString) {
        String[] parts = dateString.split("/");
        if (parts.length != 3) {
            throw new InvalidDateFormatException();
        }
        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);
        return new Date(day, month, year);
    }
    @Override
    public String toString() {
        return String.format("%02d/%02d/%d", day, month, year);
    }
    public LocalDate toLocalDate() {
        return LocalDate.of(this.year, this.month, this.day);
    }
    public static Date toDate(LocalDate localDate) {
        return new Date(localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear());
    }
}
