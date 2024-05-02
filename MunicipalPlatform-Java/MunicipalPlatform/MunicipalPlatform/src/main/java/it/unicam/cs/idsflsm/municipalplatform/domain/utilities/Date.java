package it.unicam.cs.idsflsm.municipalplatform.domain.utilities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Date {
    private int day;
    private int month;
    private int year;
}
